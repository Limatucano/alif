package com.tcc.alif.view.ui.administrator.employees

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.databinding.EmployeesItemBinding

class EmployeeAdapter(
    private val context: Context,
    val action: (item: SigninResponse) -> Unit
) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>(){

    var employees: List<SigninResponse> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(
        private val binding: EmployeesItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SigninResponse) = binding.run{
            name.text = item.name
            cpf.text = item.cpf

            deleteButton.setOnClickListener {
                action.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EmployeesItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(employees[position])
    }

    override fun getItemCount(): Int = employees.size
}