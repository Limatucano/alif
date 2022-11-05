package com.tcc.alif.view.ui.administrator.queues

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.Queues
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentQueuesBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueuesFragment(
    private val company : CompanyResponse
) : BaseFragment<FragmentQueuesBinding>(FragmentQueuesBinding::inflate) {

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
                idCompany = company.idCompany.toString()
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
        searchField.setOnQueryTextListener(
            onTextChanged = { text ->
                viewModel.handleIntent(
                    intent = QueuesIntent.GetQueuesBy(
                        idCompany = company.idCompany.toString(),
                        filter = text
                    )
                )
            },
            onSubmitClicked = { text ->
                viewModel.handleIntent(
                    intent = QueuesIntent.GetQueuesBy(
                        idCompany = company.idCompany.toString(),
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
            }
        }
    }

    private fun setAdapterItems(queues: Queues){
        adapter.queues = queues
    }

    private fun queueSelected(queue: QueueResponse){

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