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
import com.tcc.alif.databinding.FragmentModeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModeFragment : Fragment() {

    private val viewBinding : FragmentModeBinding by viewBinding()
    private lateinit var direction : NavDirections

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        viewBinding.continueButton.setOnClickListener {
            val idChecked = viewBinding.grupoRadio.checkedRadioButtonId

            if(idChecked == -1){
                Toast.makeText(requireContext(),"Selecione um modo para prosseguir", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val radioButtonSelected = view.findViewById<RadioButton>(idChecked)
            val mode : String = radioButtonSelected.tag.toString()
            direction = ModeFragmentDirections.actionModeFragmentToLoginFragment2(mode)
            view.findNavController().navigate(direction)

        }
    }
}