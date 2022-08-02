package com.tcc.alif.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.data.model.*
import com.tcc.alif.data.util.animateVisibility
import com.tcc.alif.databinding.CompanyItemBinding
import com.tcc.alif.databinding.QueuesHomeItemBinding

class QueuesAdapter(
    private val context : Context,
    private val queues : Queues,
    val action : (item : QueueResponse) -> Unit
) : RecyclerView.Adapter<QueuesAdapter.ViewHolder>(){


    inner class ViewHolder(private val binding : QueuesHomeItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(queue : QueueResponse) = binding.run{
            queueName.text = queue.name

            root.setOnClickListener {
                val isVisible = previewList.visibility != View.VISIBLE
                previewList.animateVisibility(
                    visible = isVisible,
                    useInvisible = false,
                    inAnimationId = R.anim.anim_fade_in,
                    outAnimationId = null
                )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QueuesHomeItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(queues.queues[position])
    }

    override fun getItemCount(): Int = queues.queues.size

    companion object {
        const val ROTATE_TO_DEFAULT = 0F
        const val ANIMATION_ROTATE_180 = 180F
        const val ANIMATION_TIME = 400L
    }
}