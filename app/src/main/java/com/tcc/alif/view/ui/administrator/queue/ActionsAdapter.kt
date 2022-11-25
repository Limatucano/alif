package com.tcc.alif.view.ui.administrator.queue

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.local.StatusQueue
import com.tcc.alif.databinding.ActionsQueueItemBinding

class ActionsAdapter(
    private val context: Context,
    private val action: (StatusQueue) -> Unit
) : RecyclerView.Adapter<ActionsAdapter.ViewHolder>(){

    private var actionsByStatus : List<StatusQueue> = StatusQueue.values().toList()

    inner class ViewHolder(
      private val binding: ActionsQueueItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

      fun bind(item: StatusQueue) = binding.run{
          actionTitle.text = context.getString(item.buttonText)
          actionCard.setOnClickListener {
              action.invoke(item)
          }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActionsQueueItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            item = actionsByStatus[position]
        )
    }

    override fun getItemCount(): Int = actionsByStatus.size
}