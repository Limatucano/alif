package com.tcc.alif.view.ui.administrator.configuration.mycategories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.CategoryResponse
import com.tcc.alif.databinding.CategoriesItemBinding

class MyCategoriesAdapter(
    private val context: Context,
    val action: (item : CategoryResponse) -> Unit
) : RecyclerView.Adapter<MyCategoriesAdapter.ViewHolder>() {

    var categories : List<CategoryResponse> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(category: CategoryResponse) = binding.run {
            categoryName.text = category.name

            actionIv.setOnClickListener {
                action.invoke(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoriesItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}