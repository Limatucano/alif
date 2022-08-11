package com.tcc.alif.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
open class BaseFragment<V: ViewBinding>(private val inflate : Inflate<V>) : Fragment() {

    private lateinit var _binding : V
    val binding get() = _binding


    fun setupToolbar(
        title : String = "",
        navigationBack : Boolean = true,
        visibility : Boolean = true
    ){
        (requireActivity() as BaseActivity<*>).setupToolbar(
            title = title,
            navigationBack = navigationBack,
            visibility = visibility
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater,container,false)
        return binding.root
    }

}