package com.tcc.alif.view.ui.administrator.queues

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.tcc.alif.R
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.local.StatusQueue
import com.tcc.alif.data.util.DateFormats
import com.tcc.alif.data.util.emptyIfNull
import com.tcc.alif.data.util.toStringDate
import com.tcc.alif.databinding.FragmentQueueFormBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueueFormFragment : BaseFragment<FragmentQueueFormBinding>(FragmentQueueFormBinding::inflate) {

    private val viewModel : QueuesViewModel by viewModels()
    private var queue: QueueResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = QueueFormFragmentArgs.fromBundle(bundle)
        queue = args.queue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.company_form_title),
            navigationBack = true
        )
        setViews()
        setListener()
        setObservers()
    }

    private fun setViews() = binding.run{
        val statusAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            StatusQueue.values()
        )
        statusAc.setAdapter(statusAdapter)
        fillViews()
    }

    private fun fillViews() = binding.run {
        queue?.let { queue ->
            nameQueueEt.setText(queue.name)
            averageTimeEt.setText(queue.averageTime.toString().emptyIfNull())
            descriptionEt.setText(queue.description)
            closingTimeEt.setText(queue.closingTime.toDate().toStringDate(DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT))
            openingTimeEt.setText(queue.openingTime.toDate().toStringDate(DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT))
            quantityEt.setText(queue.quantity.toString().emptyIfNull())
        }
    }

    private fun setListener() = binding.run {
        save.setOnClickListener {

        }
    }

    private fun setObservers(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){

            }
        }
    }

}