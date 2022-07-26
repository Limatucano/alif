package com.tcc.alif.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.data.domain.MeusClientesFilaData
import com.tcc.alif.view.ui.lojista.DetalheFilaHomeActivity


class MeusClientesFilaAdapter(private val items: List<MeusClientesFilaData>, var clickListener: DetalheFilaHomeActivity) : RecyclerView.Adapter<MeusClientesFilaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meus_clientes_fila,parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.initialize(items.get(position),clickListener,position)
        holder.bindView(item,position)
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nomeCliente = itemView.findViewById<TextView>(R.id.nome_cliente)
        val posicao = itemView.findViewById<TextView>(R.id.posicao)
        var posicaoFormatted = ""
        fun bindView(item: MeusClientesFilaData, position: Int) = with(itemView){

            item.let{
                posicaoFormatted = "${(position + 1).toString()}°"
                nomeCliente.text = item.nome_completo
                posicao.text = posicaoFormatted

            }

        }
        fun initialize(item: MeusClientesFilaData, action: DetalheFilaHomeActivity, position: Int){
            posicaoFormatted = "${(position + 1).toString()}°"
            nomeCliente.text = item.nome_completo
            posicao.text = posicaoFormatted

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnClickItemListener{
        fun onItemClick(items: MeusClientesFilaData, position: Int)
    }

}