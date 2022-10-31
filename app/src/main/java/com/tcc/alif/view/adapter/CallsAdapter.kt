package com.tcc.alif.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.Call
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.util.setVisible
import com.tcc.alif.databinding.CallItemBinding
import com.tcc.alif.view.ui.ItemTouchHelperListener
import java.util.*

class CallsAdapter(
    private val context : Context,
    val action : (call : Call) -> Unit
) : RecyclerView.Adapter<CallsAdapter.ViewHolder>(),
    ItemTouchHelperListener{

    var calls: List<Call> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding : CallItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            call : Call,
            previousCall: Call?
        ) = binding.run {

            sectionTitle.text = context.getString(call.status.text)
            previousCall?.status?.equals(call.status)?.also { equal ->
                if(equal){
                    sectionTitle.setVisible(false)
                    return@also
                }
                sectionTitle.setVisible(true)
            }
            consumerName.text = call.consumerName
            consumerCpf.text = call.cpf
            consumerCellphone.text = call.cellphone

            detailIv.setOnClickListener {
                action.invoke(call)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CallItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            call = calls[position],
            previousCall = calls.getOrNull(position - 1)
        )
    }

    override fun getItemCount(): Int = calls.size

    override fun onItemMove(
        recyclerView: RecyclerView,
        fromPosition: Int,
        toPosition: Int
    ): Boolean {
        if(fromPosition < toPosition){
            for(i in fromPosition until toPosition){
                if(i <= calls.size){
                    Collections.swap(calls.toMutableList(), i, i+1)
                }
            }
        }else{
            for(i in fromPosition downTo toPosition){
                if(i > 0){
                    Collections.swap(calls.toMutableList(), i, i-1)
                }
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int) {

    }
}