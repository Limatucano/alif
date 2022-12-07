package com.tcc.alif.view.ui.consumer.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.HistoricService
import com.tcc.alif.data.util.DateFormats
import com.tcc.alif.data.util.toStringDate
import com.tcc.alif.databinding.ItemHistoricBinding

class HistoricAdapter(
    private val context: Context
) : RecyclerView.Adapter<HistoricAdapter.ViewHolder>(){

    var historicService: List<HistoricService> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(
        private val binding: ItemHistoricBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: HistoricService) = binding.run {
            queueName.text = item.queueName
            historicInsertedDate.text = item.insertedDate.toDate().toStringDate(DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT)
            historicStatus.text = context.getString(item.status.text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoricBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historicService[position])
    }

    override fun getItemCount(): Int = historicService.size
}