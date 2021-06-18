package com.tcc.alif.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.model.domain.MinhasFilasData



class MinhasFilasAdapter(private val items: List<MinhasFilasData>) : RecyclerView.Adapter<MinhasFilasAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.minhas_filas_item,parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bindView(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindView(item: MinhasFilasData) = with(itemView){

            val tvSpecies = findViewById<TextView>(R.id.nome_da_fila)

            item?.let{
                tvSpecies.text = item.nome_da_fila
            }

        }
    }


}