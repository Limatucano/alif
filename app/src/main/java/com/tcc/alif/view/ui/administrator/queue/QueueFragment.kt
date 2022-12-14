package com.tcc.alif.view.ui.administrator.queue

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import com.tcc.alif.data.model.Call
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.local.StatusQueue
import com.tcc.alif.data.model.local.StatusQueue.Companion.getByStringRes
import com.tcc.alif.data.util.*
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT
import com.tcc.alif.databinding.FragmentQueueBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueueFragment : BaseFragment<FragmentQueueBinding>(FragmentQueueBinding::inflate) {

    private lateinit var queue : QueueResponse
    private lateinit var callsAdapter: CallsAdapter
    private lateinit var currentStatusQueue : StatusQueue
    private val actionsAdapter: ActionsAdapter by lazy {
        ActionsAdapter(
            context = requireContext(),
            action = { updateQueueStatus(it) }
        )
    }
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
        inflateMenu(R.menu.menu_queue)
    }

    private fun setAdapter(){
        callsAdapter = CallsAdapter(
            context = requireContext(),
            action = { selectedCall(it) }
        )
        binding.rvActions.adapter = actionsAdapter
        binding.rvCalls.adapter = callsAdapter
    }

    private fun setViews() = binding.run{
        titleTv.text = queue.name
        descricaoTv.text = queue.description
        quantityTotalTv.text = resources.getString(R.string.quantity_total, queue.quantity.toString().emptyIfNull()).fromHtml()

        setCurrentStatus(getByStringRes(queue.status))
        rvActions.setGridLayout(
            context = requireContext(),
            spanCount = 2
        )

        rvCalls.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = true
        )
    }

    private fun setCurrentStatus(status: StatusQueue) = binding.run{
        currentStatusQueue = status
        val currentStatus = requireContext().getString(status.text)
        val openingTime = queue.openingTime.toDate().toStringDate(NORMAL_DATE_WITH_HOURS_FORMAT)
        val closingTime = queue.closingTime.toDate().toStringDate(NORMAL_DATE_WITH_HOURS_FORMAT)
        statusTv.text = resources.getString(R.string.queue_status, currentStatus).fromHtml()
        when(status){
            StatusQueue.OPEN -> {
                openTv.text = resources.getString(R.string.queue_opened, openingTime).fromHtml()
                closeTv.text = resources.getString(R.string.queue_will_close, closingTime).fromHtml()
            }
            StatusQueue.CLOSED -> {
                openTv.text = resources.getString(R.string.queue_will_open, openingTime).fromHtml()
                closeTv.text = resources.getString(R.string.queue_closed, closingTime).fromHtml()
            }
        }
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
                is QueueState.Error -> {
                    Toast.makeText(requireContext(), "ERRO ${state.message}", Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
                is QueueState.QueueUpdated -> {
                    setCurrentStatus(currentStatusQueue)
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
            }
        }
    }

    private fun setListener(){
        binding.queueSwipe.setOnRefreshListener {
            viewModel.handleIntent(QueueIntent.GetCalls(queue.idQueue))
        }
        menuItemListener{ item ->
            when(item.itemId){
                R.id.action_qrCode -> {
                    requireView().findNavController().navigate(
                        QueueFragmentDirections.actionQueueFragmentToQrCodeFragment2(queue.idQueue)
                    )
                }
            }
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

    private fun updateQueueStatus(status: StatusQueue){
        currentStatusQueue = status
        viewModel.handleIntent(
            QueueIntent.UpdateQueueStatus(
                status = status,
                idQueue = queue.idQueue
            )
        )
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
                idEmployee = sharedPreferences.userId ?: EMPTY_STRING,
                idQueue = idQueue
            )
        )
    }

    private fun updateLoading(loading : Boolean) = binding.run{
        queueSwipe.isRefreshing = loading
    }
}