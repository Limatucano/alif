package com.tcc.alif.view.ui.administrator.configuration.changepassword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.tcc.alif.R
import com.tcc.alif.databinding.FragmentChangepasswordBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment: BaseFragment<FragmentChangepasswordBinding>(FragmentChangepasswordBinding::inflate) {

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.change_password)
        )

        setListener()
        setObserver()
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is ChangePasswordState.Loading -> setLoading(state.loading)
                is ChangePasswordState.PasswordValidated -> {
                    viewModel.handleIntent(ChangePasswordIntent.UpdatePassword(
                        newPassword = state.newPassword
                    ))
                }
                is ChangePasswordState.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                is ChangePasswordState.PasswordUpdated -> {
                    setLoading(false)
                    sharedPreferences.userPassword = state.password
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) = binding.run{
        changePasswodSwipe.isRefreshing = loading
    }

    //TODO: Improve error message when update password
    private fun setListener() = binding.run{
        save.setOnClickListener {
            val currentPassword = currentPasswordEt.text.toString()
            val newPassword = newPasswordEt.text.toString()
            val confirmPassword = confirmNewPasswordEt.text.toString()

            viewModel.handleIntent(ChangePasswordIntent.ValidatePassword(
                    validatePassword = sharedPreferences.userPassword ?: "",
                    currentPassword = currentPassword,
                    newPassword = newPassword,
                    confirmPassword = confirmPassword
                )
            )
        }
    }
}