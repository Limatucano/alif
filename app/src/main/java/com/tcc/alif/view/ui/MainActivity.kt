package com.tcc.alif.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityMainBinding
import com.tcc.alif.model.ClientInfo
import com.tcc.alif.model.ClientSerializable
import com.tcc.alif.model.RestApiService

class MainActivity : AppCompatActivity() {

    private val viewBinding : ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewBinding.progressLoading.visibility = View.INVISIBLE
        var mensagem = intent.getSerializableExtra("success")
        if(mensagem.toString() == "0"){
            Snackbar.make(viewBinding.Layout, R.string.cadastrado_sucesso, Snackbar.LENGTH_LONG ).show()
        }

        viewBinding.btnLogin.setOnClickListener {
            val apiService = RestApiService()
            val data = ClientInfo(
                    email = viewBinding.email.text.toString(),
                    senha = viewBinding.senha.text.toString()
            )
            viewBinding.progressLoading.visibility = View.VISIBLE
            apiService.login(data) {status: Int?, clientInfo: ClientInfo? ->
                viewBinding.progressLoading.isIndeterminate = true
                if (status != 200) {
                    viewBinding.progressLoading.isIndeterminate = false
                    Snackbar.make(viewBinding.Layout, R.string.erro_autenticacao, Snackbar.LENGTH_LONG ).show()
                }else{
                    viewBinding.progressLoading.isIndeterminate = false
                    val intent = Intent(this, ClienteActivity::class.java)
                    startActivity(intent)
                }
            }

        }
        viewBinding.btnCadastrar.setOnClickListener {
            val apresentacao = Intent(this, ApresentacaoActivity::class.java)
            startActivity(apresentacao)
        }
    }
}