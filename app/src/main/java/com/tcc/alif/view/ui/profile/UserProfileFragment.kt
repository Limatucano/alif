package com.tcc.alif.view.ui.profile

import android.os.Bundle
import android.view.View
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_FORMAT
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT
import com.tcc.alif.data.util.toStringDate
import com.tcc.alif.databinding.FragmentUserProfileBinding
import com.tcc.alif.view.ui.BaseFragment

class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
    }

    private fun setViews() = binding.run {
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