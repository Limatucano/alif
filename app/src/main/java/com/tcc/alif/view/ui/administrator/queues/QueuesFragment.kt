package com.tcc.alif.view.ui.administrator.queues

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.QueueRequest
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.Queues
import com.tcc.alif.data.util.Constants.STATE_NOT_MAPPED
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentQueuesBinding
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.administrator.MainAdministratorFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueuesFragment : BaseFragment<FragmentQueuesBinding>(FragmentQueuesBinding::inflate) {

    private val viewModel : QueuesViewModel by viewModels()
    private lateinit var adapter: QueuesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setViews()
        setAdapter()
        setObserver()
        setListeners()
        setupToolbar(
            title = getString(R.string.queues_configuration)
        )
        viewModel.handleIntent(
            intent = QueuesIntent.GetQueuesBy(
                idCompany = sharedPreferences.companyId.toString()
            )
        )
    }

    private fun setViews() = binding.run{
        rvQueuesAll.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = true
        )
    }

    private fun setListeners() = binding.run{
        addQueue.setOnClickListener {
            val direction = MainAdministratorFragmentDirections.actionMainAdministratorFragmentToQueueFormFragment2(
                QueueResponse(
                    idCompany = sharedPreferences.companyId ?: EMPTY_STRING
                )
            )
            findNavController().navigate(direction)
        }

        searchField.setOnQueryTextListener(
            onTextChanged = { text ->
                viewModel.handleIntent(
                    intent = QueuesIntent.GetQueuesBy(
                        idCompany = sharedPreferences.companyId.toString(),
                        filter = text
                    )
                )
            },
            onSubmitClicked = { text ->
                viewModel.handleIntent(
                    intent = QueuesIntent.GetQueuesBy(
                        idCompany = sharedPreferences.companyId.toString(),
                        filter = text
                    )
                )
            }
        )
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is QueuesState.Error -> Toast.makeText(requireContext(),state.message,Toast.LENGTH_SHORT).show()
                is QueuesState.Loading -> updateLoading(state.loading)
                is QueuesState.QueuesData -> {
                    setAdapterItems(state.queues)
                    updateLoading(false)
                }
                else -> throw Exception(STATE_NOT_MAPPED)
            }
        }
    }

    private fun setAdapterItems(queues: Queues){
        adapter.queues = queues
    }

    private fun queueSelected(queue: QueueResponse){
        val direction = MainAdministratorFragmentDirections.actionMainAdministratorFragmentToQueueFormFragment2(queue)
        requireView().findNavController().navigate(direction)
    }

    private fun setAdapter() = binding.run{
        adapter = QueuesListAdapter(
            context = requireContext(),
        ){
            queueSelected(it)
        }
        rvQueuesAll.adapter = adapter
    }

    private fun updateLoading(loading: Boolean) = binding.run{
        queuesSwipe.isRefreshing = loading
    }

}