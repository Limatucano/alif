package com.tcc.alif.view.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.tcc.alif.R
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_FORMAT
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT
import com.tcc.alif.data.util.MaskUtils.setCellphoneMask
import com.tcc.alif.data.util.MaskUtils.setCpfMask
import com.tcc.alif.data.util.emptyIfNull
import com.tcc.alif.data.util.toStringDate
import com.tcc.alif.databinding.FragmentUserProfileBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate){

    private val viewModel: UserProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.perfil)
        )
        setViews()
        setListener()
        setObserver()
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is UserProfileState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
                is UserProfileState.Loading -> updateLoading(state.loading)
                is UserProfileState.UserDataUpdated -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateSharedPreferences()
                    updateLoading(false)
                }
            }
        }
    }

    private fun updateSharedPreferences() = binding.run {
        sharedPreferences.run{
            userName = nameEt.text.toString().emptyIfNull()
            userDocument = cpfEt.text.toString().emptyIfNull()
            userCellphone = cellphoneEt.text.toString().emptyIfNull()
            userBirthday = birthDay.text.emptyIfNull()
        }
    }

    private fun updateLoading(loading: Boolean) = binding.run{
        userProfileSwipe.isRefreshing = loading
    }

    private fun setListener() = binding.run {
        save.setOnClickListener {
            viewModel.handleIntent(UserProfileIntent.UpdateUserData(
                SigninResponse(
                    name = nameEt.text.toString().emptyIfNull(),
                    cpf = cpfEt.text.toString().emptyIfNull(),
                    email = emailEt.text.toString().emptyIfNull(),
                    cellphone = cellphoneEt.text.toString().emptyIfNull(),
                    birthDate = birthDay.text.emptyIfNull(),
                    uid = sharedPreferences.userId.toString().emptyIfNull()
                )
            ))
        }

        userPicture.setOnClickListener {
            //TODO: Implement edit, display and take new picture
        }
    }

    private fun setViews() = binding.run {
        cpfEt.addTextChangedListener(cpfEt.setCpfMask())
        cellphoneEt.addTextChangedListener(cellphoneEt.setCellphoneMask())

        birthDay.setDateSelected { calendar, timeIsEnabled ->
            val dateFormat: String = if(timeIsEnabled) NORMAL_DATE_WITH_HOURS_FORMAT else NORMAL_DATE_FORMAT
            birthDay.text = calendar.time.toStringDate(dateFormat)
        }

        sharedPreferences.run{
            nameEt.setText(userName)
            cpfEt.setText(userDocument)
            emailEt.setText(userEmail)
            cellphoneEt.setText(userCellphone)
            birthDay.text = userBirthday.toString()
        }
    }
}