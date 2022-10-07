package com.tcc.alif.view.ui.mode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.data.model.local.AccountType
import com.tcc.alif.data.util.Constants.ADMINISTRATOR_MODE
import com.tcc.alif.data.util.Constants.CONSUMER_MODE
import com.tcc.alif.databinding.FragmentModeBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModeFragment : BaseFragment<FragmentModeBinding>(FragmentModeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        setListener()

        setupToolbar(
            navigationBack = false,
            visibility = false
        )
    }

    private fun setListener() = binding.run{

        consumerRadio.setOnClickListener { view ->
            navigate(
                mode = changeTagToType(
                    tag = view.tag.toString()
                )
            )
        }

        administratorRadio.setOnClickListener { view ->
            navigate(
                mode = changeTagToType(
                    tag = view.tag.toString()
                )
            )
        }

    }

    private fun changeTagToType(tag: String) : AccountType{
        return when(tag){
            ADMINISTRATOR_MODE -> AccountType.ADMINISTRATOR
            CONSUMER_MODE -> AccountType.CONSUMER
            else -> AccountType.CONSUMER
        }
    }
    private fun navigate(mode : AccountType){
        val direction = ModeFragmentDirections.actionModeFragmentToLoginFragment2(mode)
        requireView().findNavController().navigate(direction)
    }
}