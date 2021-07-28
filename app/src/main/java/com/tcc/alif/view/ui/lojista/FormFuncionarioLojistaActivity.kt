package com.tcc.alif.view.ui.lojista

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityFormFilaLojistaBinding
import com.tcc.alif.databinding.ActivityFormFuncionarioLojistaBinding
import com.tcc.alif.model.FuncionarioInfo
import com.tcc.alif.model.MessageRequest
import com.tcc.alif.model.RestApiService

class FormFuncionarioLojistaActivity : AppCompatActivity() {
    private val viewBinding : ActivityFormFuncionarioLojistaBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = RestApiService()
        setContentView(R.layout.activity_form_funcionario_lojista)

        viewBinding.btnVoltar.setOnClickListener {
            finish()
        }

        viewBinding.salvarFuncionario.setOnClickListener {
            val preferences = this.getSharedPreferences("LojistaData", Context.MODE_PRIVATE)
            val funcionarioData = FuncionarioInfo(
                viewBinding.nomeFuncionario.text.toString(),
                viewBinding.cargo.text.toString(),
                preferences.getInt("id_lojista", 0).toString().toInt(),
                viewBinding.cpf.text.toString()
            )
            apiService.insertNewFuncionario(funcionarioData){ status: Int?, response: MessageRequest? ->
                finish()
            }
        }
    }
}