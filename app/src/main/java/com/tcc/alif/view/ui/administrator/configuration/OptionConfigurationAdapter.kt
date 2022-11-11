package com.tcc.alif.view.ui.administrator.configuration

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.local.ConfigurationOptions
import com.tcc.alif.databinding.ConfigurationOptionsItemBinding

class OptionConfigurationAdapter(
    private val context: Context,
    val action: (intent: ConfigurationIntent) -> Unit
) : RecyclerView.Adapter<OptionConfigurationAdapter.ViewHolder>() {

    var options: List<ConfigurationOptions> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(
       private val binding: ConfigurationOptionsItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

       fun bind(option: ConfigurationOptions) = binding.run {
           optionIcon.setImageResource(option.icon)
           optionTitle.text = context.getText(option.title)

           root.setOnClickListener {
               action.invoke(option.intent)
           }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ConfigurationOptionsItemBinding.inflate(
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