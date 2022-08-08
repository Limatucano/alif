package com.tcc.alif.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.data.model.*
import com.tcc.alif.data.util.animateVisibility
import com.tcc.alif.data.util.setVisible
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


            val firstConsumerValue = queue.firstConsumers?.firstOrNull{it.position == FIRST_CONSUMER}?.name
            val secondConsumerValue = queue.firstConsumers?.firstOrNull{it.position == SECOND_CONSUMER}?.name
            val thirdConsumerValue = queue.firstConsumers?.firstOrNull{it.position == THIRD_CONSUMER}?.name

            firstLayout.setVisible(firstConsumerValue != null)
            secondLayout.setVisible(secondConsumerValue != null)
            thirdLayout.setVisible(thirdConsumerValue != null)

            firstConsumer.text = firstConsumerValue
            secondConsumer.text = secondConsumerValue
            thirdConsumer.text = thirdConsumerValue


            btnSeeMore.setOnClickListener {
                action.invoke(queue)
            }

            root.setOnClickListener {
                animateVisibilityDetail(binding)
            }

            arrowButton.setOnClickListener {
                animateVisibilityDetail(binding)
            }

        }
    }
    private fun animateVisibilityDetail(binding : QueuesHomeItemBinding) = binding.run{
        val isVisible = previewList.visibility != View.VISIBLE

        if(isVisible){
            arrowButton.background = AppCompatResources.getDrawable(context,R.drawable.expand_less)
        }else{
            arrowButton.background = AppCompatResources.getDrawable(context,R.drawable.expand_more)
        }

        previewList.animateVisibility(
            visible = isVisible,
            useInvisible = false,
            inAnimationId = R.anim.anim_fade_in,
            outAnimationId = null
        )
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
        const val FIRST_CONSUMER = 1
        const val SECOND_CONSUMER = 2
        const val THIRD_CONSUMER = 3
    }
}