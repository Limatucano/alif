package com.tcc.alif.view.ui.administrator.queue

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.model.Call
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT
import com.tcc.alif.data.util.emptyIfNull
import com.tcc.alif.data.util.fromHtml
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.data.util.toStringDate
import com.tcc.alif.databinding.FragmentQueueBinding
import com.tcc.alif.view.adapter.CallsAdapter
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueueFragment : BaseFragment<FragmentQueueBinding>(FragmentQueueBinding::inflate) {

    private lateinit var queue : QueueResponse
    private lateinit var callsAdapter: CallsAdapter
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
            title = getString(R.string.queue_title),
        )
        viewModel.handleIntent(QueueIntent.GetCalls(queue.idQueue))
        setAdapter()
        setObserver()
        setViews()
        setListener()
    }

    private fun setAdapter(){
        callsAdapter = CallsAdapter(
            context = requireContext(),
            action = { selectedCall(it) }
        )
        binding.rvCalls.adapter = callsAdapter
    }

    private fun setViews() = binding.run{
        titleTv.text = queue.name
        descricaoTv.text = queue.description
        quantityTotalTv.text = resources.getString(R.string.quantity_total, queue.quantity.toString().emptyIfNull()).fromHtml()

        queue.status?.let {
            val currentStatus = requireContext().getString(it)
            val openingTime = queue.openingTime.toDate().toStringDate(NORMAL_DATE_WITH_HOURS_FORMAT)
            val closingTime = queue.closingTime.toDate().toStringDate(NORMAL_DATE_WITH_HOURS_FORMAT)
            statusTv.text = resources.getString(R.string.queue_status, currentStatus).fromHtml()
            when(it){
                R.string.opened_status -> {
                    openTv.text = resources.getString(R.string.queue_opened, openingTime).fromHtml()
                    closeTv.text = resources.getString(R.string.queue_will_close, closingTime).fromHtml()
                }
                R.string.closed_status, R.string.pendent_status -> {
                    openTv.text = resources.getString(R.string.queue_will_open, openingTime).fromHtml()
                    closeTv.text = resources.getString(R.string.queue_closed, closingTime).fromHtml()
                }
            }
        }

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
                is QueueState.CallUpdated -> {
                    viewModel.handleIntent(QueueIntent.GetCalls(queue.idQueue))
                    updateLoading(false)
                }
                is QueueState.Loading -> updateLoading(state.loading)
                is QueueState.Calls -> {
                    setAdapterItems(state.calls)
                    updateAvailableQuantity(state.quantity)
                    updateLoading(false)
                }
                is QueueState.Error -> Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setListener(){
        binding.queueSwipe.setOnRefreshListener {
            viewModel.handleIntent(QueueIntent.GetCalls(queue.idQueue))
        }
    }

    private fun updateAvailableQuantity(quantity: Int) = binding.run{
        queue.quantity?.let { total ->
            val availableQuantity = total - quantity
            availableQuantityTv.text = resources.getString(R.string.queue_available_quantity, availableQuantity.toString()).fromHtml()
        }
    }
    private fun setAdapterItems(calls: List<Call>){
        callsAdapter.calls = calls
    }

    private fun selectedCall(call : Call){
        CallDetailBottomDialog(
            context = requireContext(),
            call = call,
            action = {
                updateCallStatus(
                    callsIntent = it,
                    idUser = call.idConsumer,
                    idQueue = queue.idQueue
                )
            }
        ).show()
    }

    private fun updateCallStatus(
        callsIntent: CallsIntent,
        idUser: String,
        idQueue: String
    ){
        val status = when(callsIntent){
            is CallsIntent.SetInProgress -> {
                CallStatus.IN_PROGRESS
            }
            is CallsIntent.SetToFinish -> {
                CallStatus.FINISHED
            }
            is CallsIntent.SetInHold -> {
                CallStatus.IN_HOLD
            }
        }
        viewModel.handleIntent(
            QueueIntent.UpdateCallStatus(
                status = status,
                idUser = idUser,
                idQueue = idQueue
            )
        )
    }

    private fun updateLoading(loading : Boolean) = binding.run{
        queueSwipe.isRefreshing = loading
    }
}