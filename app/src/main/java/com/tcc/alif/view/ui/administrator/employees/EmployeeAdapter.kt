package com.tcc.alif.view.ui.administrator.employees

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.local.Employee
import com.tcc.alif.data.util.setVisible
import com.tcc.alif.databinding.EmployeesItemBinding

class EmployeeAdapter(
    private val context: Context,
    val action: (item: Employee) -> Unit
) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>(){

    var employees: List<Employee> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(
        private val binding: EmployeesItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            currentEmployee: Employee,
            previousEmployee: Employee?
        ) = binding.run{

            sectionTitle.text = currentEmployee.statusRequest?.text?.let { context.getString(it) }
            previousEmployee?.statusRequest?.equals(currentEmployee.statusRequest)?.also { equal ->
                sectionTitle.setVisible(!equal)
            }
            name.text = currentEmployee.name
            cpf.text = currentEmployee.cpf

            deleteButton.setOnClickListener {
                action.invoke(currentEmployee)
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
        holder.bind(
            currentEmployee = employees[position],
            previousEmployee = employees.getOrNull(position - 1)
        )
    }

    override fun getItemCount(): Int = employees.size
}