package com.tcc.alif.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.tcc.alif.data.local.SharedPreferencesHelper

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
open class BaseFragment<V: ViewBinding>(private val inflate : Inflate<V>) : Fragment() {

    private lateinit var _binding : V
    val binding get() = _binding

    val sharedPreferences: SharedPreferencesHelper by lazy {
        (requireActivity() as MainActivity).sharedPreferences
    }

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

    fun getToolbar() : Toolbar = (requireActivity() as BaseActivity<*>).getToolbar()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater,container,false)
        return binding.root
    }

}