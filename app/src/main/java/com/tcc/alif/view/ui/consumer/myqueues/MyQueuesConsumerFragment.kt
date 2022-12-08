package com.tcc.alif.view.ui.consumer.myqueues

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentMyQueuesConsumerBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyQueuesConsumerFragment : BaseFragment<FragmentMyQueuesConsumerBinding>(FragmentMyQueuesConsumerBinding::inflate) {

    private val adapter: MyQueuesConsumerAdapter by lazy {
        MyQueuesConsumerAdapter(
            context = requireContext(),
            action = {}
        )
    }

    private val viewModel: MyQueuesConsumerViewModel by viewModels()
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        setupToolbar(
            title = getString(R.string.home_consumer_options_my_queues)
        )
        viewModel.handleIntent(
            MyQueuesConsumerIntent.GetMyQueues(sharedPreferences.userId ?: "")
        )
        setViews()
        setObserver()
        setListener()
    }

    private fun setListener() = binding.run {
        homeSwipe.setOnRefreshListener{
            viewModel.handleIntent(
                MyQueuesConsumerIntent.GetMyQueues(sharedPreferences.userId ?: "")
            )
        }
    }

    private fun setViews() = binding.run {
        myQueuesRv.adapter = adapter
        myQueuesRv.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = false
        )
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is MyQueuesConsumerState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
                is MyQueuesConsumerState.Loading -> updateLoading(state.loading)
                is MyQueuesConsumerState.MyQueues -> {
                    adapter.myQueues = state.queues
                    updateLoading(false)
                }
            }
        }
    }

    private fun updateLoading(loading: Boolean) = binding.run{
        homeSwipe.isRefreshing = loading
    }

}