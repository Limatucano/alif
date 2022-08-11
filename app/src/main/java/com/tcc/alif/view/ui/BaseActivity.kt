package com.tcc.alif.view.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.tcc.alif.R
import com.tcc.alif.data.util.setVisible
import com.tcc.alif.databinding.CustomToolbarBinding

typealias Factory<T> = (LayoutInflater) -> T

open class BaseActivity<V : ViewBinding>(val inflate : Factory<V>) : AppCompatActivity() {

    val binding: V by lazy { inflate(layoutInflater) }

    private val customToolbar: CustomToolbarBinding by lazy {
        CustomToolbarBinding.bind(findViewById(R.id.toolbarLayout))
    }

    fun setupToolbar(
        title : String = "",
        navigationBack : Boolean = false,
        visibility : Boolean = true
    ) = customToolbar.run{

        toolbar.setVisible(visibility)
        toolbar.title = title
        backIv.setVisible(navigationBack)
        backIv.setOnClickListener {
            onBackPressed()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}