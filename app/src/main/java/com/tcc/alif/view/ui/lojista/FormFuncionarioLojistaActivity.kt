package com.tcc.alif.view.ui.lojista

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityFormFilaLojistaBinding
import com.tcc.alif.databinding.ActivityFormFuncionarioLojistaBinding
import com.tcc.alif.model.FuncionarioInfo
import com.tcc.alif.model.MessageRequest
import com.tcc.alif.model.restApiService.lojistaService
import com.tcc.alif.model.util.CPFUtil
import com.tcc.alif.model.util.MaskUtils
import com.tcc.alif.model.util.ValidateUtil

class FormFuncionarioLojistaActivity : AppCompatActivity() {
    private val viewBinding : ActivityFormFuncionarioLojistaBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = lojistaService()
        setContentView(R.layout.activity_form_funcionario_lojista)
        viewBinding.cpf.addTextChangedListener(MaskUtils.cpfMask(viewBinding.cpf))
        val funcionario = intent?.getSerializableExtra("funcionario") as HashMap<*, *>?

        if(!funcionario.isNullOrEmpty()){
            viewBinding.codFuncionario.setText(funcionario["cod_funcionario"].toString())
            viewBinding.nomeFuncionario.setText(funcionario["nome_funcionario"].toString())
            viewBinding.cargo.setText(funcionario["cargo"].toString())
            viewBinding.cpf.setText(funcionario["cpf"].toString())
            viewBinding.excluirFuncionario.visibility = View.VISIBLE
        }else{
            viewBinding.excluirFuncionario.visibility = View.GONE
        }

        viewBinding.btnVoltar.setOnClickListener {
            finish()
        }

        viewBinding.excluirFuncionario.setOnClickListener {
            apiService.deleteFuncionario(viewBinding.codFuncionario.text.toString()){ status: Int?, response: MessageRequest? ->
                finish()
            }
        }
        viewBinding.salvarFuncionario.setOnClickListener {
            val preferences = this.getSharedPreferences("LojistaData", Context.MODE_PRIVATE)
            if (!CPFUtil.myValidateCPF(viewBinding.cpf.text.toString())) {
                viewBinding.cpf.error = getString(R.string.cpf_invalid_error)
                return@setOnClickListener
            }
            if(validateForm()){
                if(!funcionario.isNullOrEmpty()){
                    val funcionarioData = FuncionarioInfo(
                            nome_funcionario = viewBinding.nomeFuncionario.text.toString(),
                            cargo = viewBinding.cargo.text.toString(),
                            id_lojista = preferences.getInt("id_lojista", 0).toString().toInt(),
                            cpf = viewBinding.cpf.text.toString(),
                            cod_funcionario = viewBinding.codFuncionario.text.toString().toInt()
                    )
                    apiService.updateFuncionario(funcionarioData){ status: Int?, response: MessageRequest? ->
                        finish()
                    }
                }
                if(funcionario.isNullOrEmpty()){
                    val funcionarioData = FuncionarioInfo(
                            nome_funcionario = viewBinding.nomeFuncionario.text.toString(),
                            cargo = viewBinding.cargo.text.toString(),
                            id_lojista = preferences.getInt("id_lojista", 0).toString().toInt(),
                            cpf = viewBinding.cpf.text.toString(),
                    )
                    apiService.insertNewFuncionario(funcionarioData){ status: Int?, response: MessageRequest? ->
                        finish()
                    }
                }
            }
        }
    }
    /*
    * Tem o objetivo de ser a ponte entre a validação e os campos
    *
    * @return  boolean
    *
    * */
    fun validateForm():Boolean{
        val validateNomeFuncionario = ValidateUtil.validate(viewBinding.nomeFuncionario)
        val validateCpf = ValidateUtil.validate(viewBinding.cpf)

        if(validateNomeFuncionario.and(validateCpf)){
            return true
        }
        return false
    }
}