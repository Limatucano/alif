package com.tcc.alif.view.ui.queue

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.data.model.Calls
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentQueueBinding
import com.tcc.alif.view.adapter.CallsAdapter
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.BaseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueueFragment : BaseFragment<FragmentQueueBinding>(FragmentQueueBinding::inflate) {

    private lateinit var queue : QueueResponse
    private val viewModel : QueueViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = QueueFragmentArgs.fromBundle(bundle)
        queue = args.queue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(
            toolbar = binding.toolbar,
            title = queue.name,
        )
        setIntent()
        setObserver()
        setViews()

    }

    private fun setIntent(){
        viewModel.handleIntent(QueueIntent.GetCalls(queue.idQueue))
    }
    private fun setViews() = binding.run{
        rvCalls.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = true
        )
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is BaseState.Loading -> updateLoading(state.loading)
                is BaseState.Success<*> -> {
                    setAdapter(state.response as Calls)
                    updateLoading(false)
                }
                is BaseState.Error -> Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAdapter(calls : Calls) = binding.run {
        rvCalls.adapter = CallsAdapter(
            context = requireContext(),
            calls = calls.calls
        ){

        }
    }
    private fun updateLoading(loading : Boolean) = binding.run{
        queueSwipe.isEnabled = loading
        queueSwipe.isRefreshing = loading
    }

}