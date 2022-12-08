package com.tcc.alif.view.ui.consumer.myqueues

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.model.MyQueuesResponse
import com.tcc.alif.databinding.ItemMyQueuesBinding
import java.time.Duration
import java.time.LocalTime

class MyQueuesConsumerAdapter(
    private val context: Context,
    private val action: () -> Unit
) : RecyclerView.Adapter<MyQueuesConsumerAdapter.ViewHolder>() {

    var myQueues : List<MyQueuesResponse> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(
        private val binding: ItemMyQueuesBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(myQueue: MyQueuesResponse) = binding.run {
            queueName.text = myQueue.queueName
            companyName.text = myQueue.companyName
            if(myQueue.status == CallStatus.IN_PROGRESS){
                consumerPosition.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40F)
                consumerPosition.text = context.getString(R.string.my_queues_consumer_position_in_progress)
            } else {
                consumerPosition.text = context.getString(R.string.my_queues_consumer_position, myQueue.consumerPosition.toString())
            }
            estimateTime.text = context.getString(
                R.string.my_queues_estimated_time,
                LocalTime.MIN.plus(
                    Duration.ofMinutes(
                        myQueue.estimatedTime?.toLong() ?: 0L
                    )
                ).toString()
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyQueuesBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myQueues[position])
    }

    override fun getItemCount(): Int = myQueues.size
}