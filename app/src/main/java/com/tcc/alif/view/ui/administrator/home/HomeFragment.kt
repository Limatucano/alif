package com.tcc.alif.view.ui.administrator.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.Queues
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentHomeLojistaBinding
import com.tcc.alif.view.adapter.QueuesAdapter
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.administrator.MainAdministratorFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment(private val company : CompanyResponse) : BaseFragment<FragmentHomeLojistaBinding>(FragmentHomeLojistaBinding::inflate) {

    private val viewModel : HomeViewModel by viewModels()
    private lateinit var queuesAdapter: QueuesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setViews()
        setAdapter()
        setObserver()
        setupToolbar(
            title = getString(R.string.queues_title)
        )
        viewModel.handleIntent(
            HomeIntent.GetQueuesBy(
                idCompany = company.idCompany.toString()
            )
        )
    }

    private fun setViews() = binding.run{
        rvQueues.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = true
        )
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is HomeState.Loading -> updateLoading(state.loading)
                is HomeState.Error -> Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
                is HomeState.QueuesData -> {
                    setAdapterItems(state.queues)
                    updateLoading(false)
                }
            }
        }
    }

    private fun setAdapterItems(response : Queues){
        queuesAdapter.queues = response
    }

    private fun updateLoading(loading : Boolean) = binding.run{
        homeSwipe.isEnabled = loading
        homeSwipe.isRefreshing = loading
    }

    private fun selectedQueue(queue: QueueResponse){
        val direction = MainAdministratorFragmentDirections.actionMainAdministratorFragmentToQueueFragment(queue)
        requireView().findNavController().navigate(direction)
    }
    private fun setAdapter() = binding.run{
        queuesAdapter = QueuesAdapter(requireContext()){ queue ->
            selectedQueue(queue)
        }
        rvQueues.adapter = queuesAdapter
    }
}