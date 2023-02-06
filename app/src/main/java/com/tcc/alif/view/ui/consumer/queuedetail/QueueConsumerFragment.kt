package com.tcc.alif.view.ui.consumer.queuedetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.firebase.Timestamp
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.model.MyQueuesResponse
import com.tcc.alif.data.model.Service
import com.tcc.alif.data.model.local.StatusQueue
import com.tcc.alif.data.model.local.StatusQueue.Companion.getByStringRes
import com.tcc.alif.data.util.*
import com.tcc.alif.data.util.ValidateUtil.generateUUID
import com.tcc.alif.databinding.FragmentQueueConsumerBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueueConsumerFragment: BaseFragment<FragmentQueueConsumerBinding>(FragmentQueueConsumerBinding::inflate) {

    private val args by navArgs<QueueConsumerFragmentArgs>()
    private val viewModel: QueueConsumerViewModel by viewModels()
    private lateinit var idService: String
    private lateinit var statusQueue: StatusQueue
    private lateinit var myQueues: MyQueuesResponse

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(
            title = getString(R.string.queue_title)
        )
        statusQueue = getByStringRes(args.queue.status)
        checkIfUserIsSubscribed()
        setView()
        setListener()
        setObserver()
    }

    private fun checkIfUserIsSubscribed(){
        viewModel.handleIntent(
            QueueConsumerIntent.CheckIfUserIsSubscribed(
                idQueue = args.queue.idQueue,
                idUser = sharedPreferences.userId ?: SharedPreferencesHelper.EMPTY_STRING
            )
        )
    }
    private fun setListener() = binding.run {
        //TODO: implementar dialog com motivo do cancelamento
        cancelSubscription.setOnClickListener {
            viewModel.handleIntent(
                QueueConsumerIntent.CancelSubscription(
                    idQueue = args.queue.idQueue,
                    service = Service(
                        idService = myQueues.idService,
                        userId = myQueues.idUser,
                        enrollmentTime = myQueues.enrollmentTime,
                        status = myQueues.status.value,
                        employeeResponsible = myQueues.employeeResponsible,
                    )
                )
            )
        }
        subscribe.setOnClickListener {
            viewModel.handleIntent(
                QueueConsumerIntent.Subscribe(
                    idQueue = args.queue.idQueue,
                    service = Service(
                        idService = generateUUID(),
                        userId = sharedPreferences.userId ?: SharedPreferencesHelper.EMPTY_STRING,
                        enrollmentTime = Timestamp.now(),
                        status = CallStatus.IN_HOLD.value,
                        employeeResponsible = args.queue.employeeResponsible ?: SharedPreferencesHelper.EMPTY_STRING,
                    )
                )
            )
        }
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is QueueConsumerState.Loading -> {
                    updateLoading(state.loading)
                }
                is QueueConsumerState.Error -> {
                    updateLoading(false)
                    Toast.makeText(requireContext(),state.message,Toast.LENGTH_SHORT).show()
                }
                is QueueConsumerState.AlreadySubscribed -> {
                    updateLoading(false)
                    if(state.idService?.isNotEmpty() == true){
                        idService = state.idService
                    }
                    if(state.response != null){
                        myQueues = state.response
                    }
                    controlButton(state.isSubscribed)
                }
                is QueueConsumerState.CanceledSubscription -> {
                    updateScreen(state.message)
                }
                is QueueConsumerState.Subscribed -> {
                    updateScreen(state.message)
                }
            }
        }
    }

    private fun updateScreen(message: String){
        checkIfUserIsSubscribed()
        updateLoading(false)
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun controlButton(userIsSubscribed: Boolean) = binding.run {
        if(userIsSubscribed){
            cancelSubscription.setVisible(true)
            subscribe.setVisible(false)
        }else {
            cancelSubscription.setVisible(false)
            subscribe.setVisible(true)
        }
    }

    private fun updateLoading(loading: Boolean) = binding.run {
        queueSwipe.isRefreshing = loading
    }

    private fun setView() = binding.run{
        args.queue.let { queue ->
            titleTv.text = queue.name
            descriptionTv.text = queue.description
            averageTimeTv.text = resources.getString(R.string.queue_consumer_average_time_message, queue.averageTime.toString()).fromHtml()
            queueQuantityTv.text = resources.getString(R.string.queue_consumer_quantity_before_me, queue.quantity.toString().emptyIfNull()).fromHtml()
            val currentStatus = requireContext().getString(statusQueue.text)
            val openingTime = queue.openingTime.toDate().toStringDate(DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT)
            val closingTime = queue.closingTime.toDate().toStringDate(DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT)
            statusTv.text = resources.getString(R.string.queue_status, currentStatus).fromHtml()
            when(statusQueue){
                StatusQueue.OPEN -> {
                    openTv.text = resources.getString(R.string.queue_opened, openingTime).fromHtml()
                    closeTv.text = resources.getString(R.string.queue_will_close, closingTime).fromHtml()
                }
                StatusQueue.CLOSED -> {
                    openTv.text = resources.getString(R.string.queue_will_open, openingTime).fromHtml()
                    closeTv.text = resources.getString(R.string.queue_closed, closingTime).fromHtml()
                    cancelSubscription.setVisible(false)
                    subscribe.setVisible(false)
                }
            }
        }
    }
}