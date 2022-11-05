package com.tcc.alif.view.ui.administrator.queues

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.Queues
import com.tcc.alif.databinding.QueuesItemBinding

class QueuesListAdapter(
    private val context : Context,
    val action : (item : QueueResponse) -> Unit
) : RecyclerView.Adapter<QueuesListAdapter.ViewHolder>(){

    var queues: Queues = Queues()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding : QueuesItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(queue : QueueResponse) = binding.run{
            queueName.text = queue.name
            queueStatus.text = queue.status?.let { context.getString(it) }

            root.setOnClickListener {
                action.invoke(queue)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QueuesItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(queues.queues[position])
    }

    override fun getItemCount(): Int = queues.queues.size
}