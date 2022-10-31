package com.tcc.alif.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.data.model.Call
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.Queues
import com.tcc.alif.data.model.Service
import com.tcc.alif.data.util.animateVisibility
import com.tcc.alif.data.util.setVisible
import com.tcc.alif.databinding.QueuesHomeItemBinding

class QueuesAdapter(
    private val context : Context,
    val action : (item : QueueResponse) -> Unit
) : RecyclerView.Adapter<QueuesAdapter.ViewHolder>(){

    var queues: Queues = Queues()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding : QueuesHomeItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(queue : QueueResponse) = binding.run{
            queueName.text = queue.name
            setupFirstConsumers(queue.firstConsumers)

            queueStatus.text = queue.status?.let { context.getString(it) }

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

        private fun setupFirstConsumers(services: List<Service>) = binding.run{

            val firstConsumerValue = services.getOrNull(0)?.name
            val secondConsumerValue = services.getOrNull(1)?.name
            val thirdConsumerValue = services.getOrNull(2)?.name

            firstLayout.setVisible( firstConsumerValue != null)
            secondLayout.setVisible(secondConsumerValue != null)
            thirdLayout.setVisible(thirdConsumerValue != null)

            firstConsumer.text = firstConsumerValue
            secondConsumer.text = secondConsumerValue
            thirdConsumer.text = thirdConsumerValue
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
}