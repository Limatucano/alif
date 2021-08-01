package com.tcc.alif.view.adapter

import android.graphics.drawable.Drawable
import android.os.Build
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.model.domain.MinhasFilasData



class MinhasFilasHomeAdapter(private val items: List<MinhasFilasData>,var clickListener: OnClickItemListener) : RecyclerView.Adapter<MinhasFilasHomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.minhas_filas_home_item,parent, false)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.initialize(items[position],clickListener)
        holder.bindView(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val tvFila = itemView.findViewById<TextView>(R.id.filaName)
        private val arrow_button: ImageButton = itemView.findViewById(R.id.arrow_button)
        private val hidden_view: LinearLayout = itemView.findViewById(R.id.hidden_view)
        private val base_cardView = itemView.findViewById<CardView>(R.id.base_cardView)
        private val btnVerMais = itemView.findViewById<TextView>(R.id.btnVerMais)
        fun bindView(item: MinhasFilasData) = with(itemView){

            item.let{
                tvFila.text = item.nome_da_fila
            }

        }
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun initialize(item: MinhasFilasData, action: OnClickItemListener){
            tvFila.text = item.nome_da_fila

            btnVerMais.setOnClickListener {
                Log.d("TESTE", "AAAAAAAAA")
            }
            arrow_button.setOnClickListener {
                if(hidden_view.visibility == View.VISIBLE){
                    hidden_view.visibility = View.GONE
                    arrow_button.setBackgroundResource(R.drawable.expand_more)
                }else{
                    hidden_view.visibility = View.VISIBLE
                    arrow_button.setBackgroundResource(R.drawable.expand_less)
                }
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnClickItemListener{
        fun onItemClick(items: MinhasFilasData, position: Int)
    }

}