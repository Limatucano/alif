package com.tcc.alif.view.ui.consumer.queues

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentQueuesConsumerBinding
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.consumer.qrcode.QrCodeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueuesConsumerFragment : BaseFragment<FragmentQueuesConsumerBinding>(FragmentQueuesConsumerBinding::inflate) {

    private val viewModel: QueuesConsumerViewModel by viewModels()

    private val adapter: QueuesConsumerAdapter by lazy {
        QueuesConsumerAdapter(
            context = requireContext(),
            action = { queue ->
                requireView()
                    .findNavController()
                    .navigate(
                        QueuesConsumerFragmentDirections
                            .toQueueConsumer(queue)
                    )
            }
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(
            title = getString(R.string.queues_title)
        )
        setListener()
        setView()
        setObserver()
    }

    private fun setView() = binding.run {
        rvQueuesAll.adapter = adapter
        rvQueuesAll.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = false
        )
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is QueuesConsumerState.Queues -> {
                    adapter.queues = state.queues
                    updateLoading(false)
                }
                is QueuesConsumerState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
                is QueuesConsumerState.Loading -> updateLoading(state.loading)
            }
        }

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(QrCodeFragment.QR_CODE_KEY)
            ?.observe(viewLifecycleOwner){ qrCode ->
                viewModel.handleIntent(QueuesConsumerIntent.SearchQueues(
                    filter = qrCode,
                    byQrCode = true
                ))
            }
    }

    private fun updateLoading(loading: Boolean) = binding.run {
        queuesSwipe.isRefreshing = loading
    }

    private fun setListener() = binding.run {
        searchField.setOnQueryTextListener(
            onTextChanged = {
                viewModel.handleIntent(QueuesConsumerIntent.SearchQueues(it))
            },
            onSubmitClicked = {
                viewModel.handleIntent(QueuesConsumerIntent.SearchQueues(it))
            }
        )

        qrCode.setOnClickListener {
            requireView().findNavController().navigate(QueuesConsumerFragmentDirections.toQrCode())
        }
    }
}