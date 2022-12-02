package com.tcc.alif.view.ui.consumer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.local.HomeConsumerOptions
import com.tcc.alif.databinding.ItemHomeConsumerBinding

class HomeOptionsAdapter(
    private val context: Context,
    private val action: (HomeConsumerOptions) -> Unit
) : RecyclerView.Adapter<HomeOptionsAdapter.ViewHolder>() {

    var options: List<HomeConsumerOptions> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(
        private val binding: ItemHomeConsumerBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: HomeConsumerOptions) = binding.run {
            iconOption.setImageResource(item.icon)
            titleOption.text = context.getText(item.title)

            root.setOnClickListener {
                action.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeConsumerBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount(): Int = options.size
}