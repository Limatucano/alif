package com.tcc.alif.view.ui.administrator.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.data.model.Company
import com.tcc.alif.data.model.Queues
import com.tcc.alif.databinding.FragmentHomeLojistaBinding
import com.tcc.alif.view.adapter.QueuesAdapter
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.login.SigninState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment(private val company : Company) : BaseFragment<FragmentHomeLojistaBinding>(FragmentHomeLojistaBinding::inflate) {

    private val viewModel : HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rvQueues.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        viewModel.handleIntent(
            HomeIntent.getQueuesBy(
                idCompany = company.idCompany.toString()
            )
        )
        setObserver()

    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is HomeState.Loading -> Toast.makeText(requireContext(),"ALOOO ${state.loading}",
                    Toast.LENGTH_SHORT).show()
                is HomeState.Error -> Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
                is HomeState.Success -> setViews(state.response)
            }
        }
    }

    private fun setViews(response : Queues) = binding.run{
        rvQueues.adapter = QueuesAdapter(requireContext(),response){

        }
    }
}