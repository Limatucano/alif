package com.tcc.alif.view.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.tcc.alif.data.model.Signin
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.util.Constants.ADMINISTRATOR_MODE
import com.tcc.alif.data.util.Constants.CONSUMER_MODE
import com.tcc.alif.data.util.Constants.MOCK_EMAIL
import com.tcc.alif.data.util.Constants.MOCK_PASS
import com.tcc.alif.data.util.ValidateUtil
import com.tcc.alif.databinding.FragmentLoginBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel : LoginViewModel by viewModels()
    private var mode : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = LoginFragmentArgs.fromBundle(bundle)
        mode = args.mode
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
    }

    private fun setObservers() = loginViewModel.run{
        user.observe(viewLifecycleOwner){ state ->
            when(state){
                is SigninState.Loading -> Toast.makeText(requireContext(),"ALOOO ${state.loading}",Toast.LENGTH_SHORT).show()
                is SigninState.Error -> Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
                is SigninState.SuccessSignin -> openHomeScreen(state.response)
            }
        }
    }

    private fun setListeners() = binding.run {
        loginButton.setOnClickListener {

            emailEdit.setText(MOCK_EMAIL)
            passwordEdit.setText(MOCK_PASS)
            if(validateFields()){
                loginViewModel.handleIntent(SigninIntent.Signin(
                    Signin(
                        email = emailEdit.text.toString(),
                        password = passwordEdit.text.toString(),
                        type = mode
                    )
                ))
            }

        }

        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnCadastrar.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToSplashFragment()
            requireView().findNavController().navigate(direction)
        }
    }

    private fun openHomeScreen(signinResponse : SigninResponse){
        when(mode){
            ADMINISTRATOR_MODE -> {
                val direction = LoginFragmentDirections.actionLoginFragmentToCompaniesFragment(signinResponse)
                requireView().findNavController().navigate(direction)
            }
            CONSUMER_MODE -> {
                val direction = LoginFragmentDirections.actionLoginFragmentToHomeClienteFragment(signinResponse)
                requireView().findNavController().navigate(direction)
            }
        }
    }
    private fun validateFields() : Boolean = binding.run { ValidateUtil.validate(emailEdit) && ValidateUtil.validate(passwordEdit) }


}