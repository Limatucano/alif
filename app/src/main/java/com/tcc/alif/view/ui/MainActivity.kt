package com.tcc.alif.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityMainBinding
import com.tcc.alif.model.ClientSerializable

class MainActivity : AppCompatActivity() {

    private val viewBinding : ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mensagem = intent.getSerializableExtra("success")
        Log.d("TESTANDO", mensagem.toString())
        if(mensagem.toString() == "0"){
            Snackbar.make(viewBinding.Layout, R.string.cadastrado_sucesso, Snackbar.LENGTH_LONG ).show()
        }

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