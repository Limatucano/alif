package com.tcc.alif.view.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.tcc.alif.data.model.Signin
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.model.local.AccountType
import com.tcc.alif.data.util.ValidateUtil
import com.tcc.alif.databinding.FragmentLoginBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel : LoginViewModel by viewModels()
    private lateinit var mode : AccountType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = LoginFragmentArgs.fromBundle(bundle)
        mode = args.mode
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            navigationBack = true,
        )
        setListeners()
        setObservers()
    }

    private fun setObservers() = loginViewModel.run{
        state.observe(viewLifecycleOwner){ state ->
            when(state){
                is SigninState.Loading -> updateLoading(state.loading)
                is SigninState.Error -> {
                    updateLoading(false)
                    Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
                }
                is SigninState.Success -> {
                    updateLoading(false)
                    setupSharedPreferences(state.user)
                    openHomeScreen(state.user)
                }
            }
        }
    }

    private fun setupSharedPreferences(user: SigninResponse) = sharedPreferences.run{
        userId = user.uid
        userEmail = user.email
        userPassword = binding.passwordEdit.text.toString()
        userName = user.name
        userCellphone = user.cellphone
        userBirthday = user.birthDate
        userDocument = user.cpf
    }

    private fun setListeners() = binding.run {
        loginButton.setOnClickListener {
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

        btnCadastrar.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToSplashFragment()
            requireView().findNavController().navigate(direction)
        }
    }

    private fun openHomeScreen(signinResponse : SigninResponse){
        when(mode){
            AccountType.ADMINISTRATOR -> {
                val direction = LoginFragmentDirections.actionLoginFragmentToCompaniesFragment(signinResponse)
                requireView().findNavController().navigate(direction)
            }
            AccountType.CONSUMER -> {
                val direction = LoginFragmentDirections.actionLoginFragmentToHomeClienteFragment(signinResponse)
                requireView().findNavController().navigate(direction)
            }
        }
    }

    private fun updateLoading(loading : Boolean) = binding.run{
        loginSwipe.isEnabled = loading
        loginSwipe.isRefreshing = loading
    }

    private fun validateFields() : Boolean = binding.run { ValidateUtil.validate(emailEdit) && ValidateUtil.validate(passwordEdit) }

}