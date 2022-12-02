package com.tcc.alif.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

    fun getToolbar() : Toolbar = customToolbar.toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showStatusBar(true)
    }

    private fun showStatusBar(darkIcons: Boolean = false) {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        if (darkIcons) window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    private fun setWindowFlag(
        bits: Int,
        on: Boolean
    ) {
        val win = window
        val winParams = win.attributes
        winParams.flags = if (on) winParams.flags or bits else winParams.flags and bits.inv()
        win.attributes = winParams
    }
}