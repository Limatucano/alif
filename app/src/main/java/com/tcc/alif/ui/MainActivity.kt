package com.tcc.alif.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewBinding : ActivityMainBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBinding.btnLogin.setOnClickListener {
            val intent = Intent(this, ModoActivity::class.java)
            startActivity(intent)
        }
        viewBinding.btnCadastrar.setOnClickListener {
            val apresentacao = Intent(this, ApresentacaoActivity::class.java)
            startActivity(apresentacao)
        }
    }
}