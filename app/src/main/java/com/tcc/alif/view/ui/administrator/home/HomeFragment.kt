package com.tcc.alif.view.ui.administrator.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.model.Company
import com.tcc.alif.data.model.Queues
import com.tcc.alif.databinding.FragmentHomeLojistaBinding
import com.tcc.alif.view.adapter.QueuesAdapter
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.administrator.MainAdministratorFragment
import com.tcc.alif.view.ui.administrator.MainAdministratorFragmentDirections
import com.tcc.alif.view.ui.login.SigninState
import com.tcc.alif.view.ui.mode.ModeFragmentDirections
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
        setupToolbar(
            toolbar = binding.toolbar,
            title = getString(R.string.queues_title),
        )
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is HomeState.Loading -> updateLoading(state.loading)
                is HomeState.Error -> Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
                is HomeState.Success -> {
                    setViews(state.response)
                    updateLoading(false)
                }
            }
        }
    }

    private fun updateLoading(loading : Boolean) = binding.run{
        homeSwipe.isEnabled = loading
        homeSwipe.isRefreshing = loading
    }
    private fun setViews(response : Queues) = binding.run{
        rvQueues.adapter = QueuesAdapter(requireContext(),response){ queue ->
            val direction = MainAdministratorFragmentDirections.actionMainAdministratorFragmentToQueueFragment(queue)
            requireView().findNavController().navigate(direction)
        }
    }
}