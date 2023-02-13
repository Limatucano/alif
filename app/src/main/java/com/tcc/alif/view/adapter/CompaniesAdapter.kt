package com.tcc.alif.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.data.model.Companies
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.databinding.CompanyItemBinding

class CompaniesAdapter(
    private val context : Context,
    private val companies : Companies,
    val action : (item : CompanyResponse) -> Unit
) : RecyclerView.Adapter<CompaniesAdapter.ViewHolder>(){


    inner class ViewHolder(private val binding : CompanyItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(company : CompanyResponse) = binding.run{
            companyName.text = company.tradeName
            companyAddress.text = "${company.street}, ${company.numberHouse}"
            companyCnpj.text = company.cnpj
            companyRole.text = company.role

            root.setOnClickListener {
                action.invoke(company)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CompanyItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(companies.companies[position])
    }

    override fun getItemCount(): Int = companies.companies.size
}