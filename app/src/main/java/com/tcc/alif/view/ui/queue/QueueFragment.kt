package com.tcc.alif.view.ui.queue

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.databinding.FragmentQueueBinding
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

    }

    private fun setIntent(){
        viewModel.handleIntent(QueueIntent.GetCalls(queue.idQueue))
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is BaseState.Loading -> { updateLoading(state.loading) }
                is BaseState.Success<*> -> { Toast.makeText(requireContext(), "Deu Bom ${state.response}", Toast.LENGTH_SHORT).show()}
                is BaseState.Error -> Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateLoading(loading : Boolean) = binding.run{
        queueSwipe.isEnabled = loading
        queueSwipe.isRefreshing = loading
    }

}