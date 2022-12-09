package com.tcc.alif.view.ui.consumer.queues

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.databinding.QueuesConsumerItemBinding

class QueuesConsumerAdapter(
    private val context: Context,
    private val action: () -> Unit
) : RecyclerView.Adapter<QueuesConsumerAdapter.ViewHolder>() {

    var queues: List<QueueResponse> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(
        private val binding: QueuesConsumerItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: QueueResponse) = binding.run {
            queueName.text = item.name
            companyName.text = item.companyName
            queueStatus.text = item.status?.let { context.getString(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QueuesConsumerItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(queues[position])
    }

    override fun getItemCount(): Int = queues.size
}