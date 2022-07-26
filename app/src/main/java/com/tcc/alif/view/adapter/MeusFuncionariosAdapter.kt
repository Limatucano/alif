package com.tcc.alif.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.data.domain.MeusFuncionariosData


class MeusFuncionariosAdapter(private val items: List<MeusFuncionariosData>, var clickListener: OnClickItemListener) : RecyclerView.Adapter<MeusFuncionariosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meus_funcionarios,parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.initialize(items.get(position),clickListener)
        holder.bindView(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nomeFuncionario = itemView.findViewById<TextView>(R.id.nome_funcionario)

        fun bindView(item: MeusFuncionariosData) = with(itemView){

            item.let{
                nomeFuncionario.text = item.nome_funcionario
            }

        }
        fun initialize(item: MeusFuncionariosData, action: OnClickItemListener){
            nomeFuncionario.text = item.nome_funcionario

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnClickItemListener{
        fun onItemClick(items: MeusFuncionariosData, position: Int)
    }

}