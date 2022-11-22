package com.tcc.alif.view.ui.administrator.configuration.businessRequests

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.BusinessRequestsResponse
import com.tcc.alif.databinding.BusinessRequestsItemBinding

class BusinessRequestsAdapter(
    private val context: Context,
    private val accept: (BusinessRequestsResponse) -> Unit,
    private val refuse: (BusinessRequestsResponse) -> Unit
) : RecyclerView.Adapter<BusinessRequestsAdapter.ViewHolder>() {

    var businessRequests : List<BusinessRequestsResponse> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(val binding: BusinessRequestsItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: BusinessRequestsResponse) = binding.run {
            companyCnpj.text = item.cnpj
            companyName.text = item.tradeName

            acceptIcon.setOnClickListener{
                accept.invoke(item)
            }
            refusedIcon.setOnClickListener {
                refuse.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BusinessRequestsItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(businessRequests[position])
    }

    override fun getItemCount(): Int = businessRequests.size
}