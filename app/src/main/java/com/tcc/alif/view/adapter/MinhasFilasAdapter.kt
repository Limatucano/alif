package com.tcc.alif.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.model.domain.MinhasFilasData



class MinhasFilasAdapter(private val items: List<MinhasFilasData>, var clickListener: OnClickItemListener, var lojista: Boolean = false) : RecyclerView.Adapter<MinhasFilasAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.minhas_filas_item,parent, false)

        if(lojista){
            view = LayoutInflater.from(parent.context).inflate(R.layout.minhas_filas_lojista,parent, false)
        }


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.initialize(items.get(position),clickListener)
        holder.bindView(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvFila = itemView.findViewById<TextView>(R.id.nome_da_fila)

        fun bindView(item: MinhasFilasData) = with(itemView){

            item.let{
                tvFila.text = item.nome_da_fila
            }

        }
        fun initialize(item: MinhasFilasData, action: OnClickItemListener){
            tvFila.text = item.nome_da_fila

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnClickItemListener{
        fun onItemClick(items: MinhasFilasData, position: Int)
    }

}