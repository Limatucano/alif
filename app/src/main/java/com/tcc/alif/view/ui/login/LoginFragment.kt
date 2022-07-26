package com.tcc.alif.view.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.tcc.alif.data.model.Signin
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

    private fun setListeners() = binding.run {
        binding.btnLogin.setOnClickListener {
            val user = Signin(
                email = "neymar@teste3",
                password = "teste",
                type = mode
            )
            loginViewModel.handleIntent(SigninIntent.Signin(user))
        }

        binding.btnCadastrar.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToSplashFragment()
            requireView().findNavController().navigate(direction)
        }
    }

    private fun setObservers() = loginViewModel.run{
        user.observe(viewLifecycleOwner){ state ->
            when(state){
                is SigninState.Loading -> Toast.makeText(requireContext(),"ALOOO ${state.loading}",Toast.LENGTH_SHORT).show()
                is SigninState.Error -> Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
                is SigninState.SuccessSignin -> Toast.makeText(requireContext(), "FOII ${state.response.toString()}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}