package com.tcc.alif.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.annotations.SerializedName
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityFirstCadstroBinding
import com.tcc.alif.model.ClientInfo
import com.tcc.alif.model.ClientSerializable
import com.tcc.alif.model.restApiService.lojistaService
import com.tcc.alif.model.restApiService.usuarioService
import com.tcc.alif.model.util.RegexUtil
import com.tcc.alif.model.util.ValidateUtil

class FirstCadstroActivity : AppCompatActivity() {

    private val viewBinding: ActivityFirstCadstroBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_cadstro)
        setupListeners()
    }
    private fun sendData(email: String, senha: String){
        val clientInfo = ClientSerializable()
        clientInfo.setEmail(email)
        clientInfo.setSenha(senha)
        val intent = Intent(this, CadastroActivity::class.java)
        val b = Bundle()
        b.putSerializable("serialzable", clientInfo)
        intent.putExtras(b)
        startActivity(intent)
    }
    private fun setupListeners(){
        val regexSenha = RegexUtil.passwordRegex()
        val regexEmail = RegexUtil.emailRegex()
        viewBinding.progressLoading.visibility = View.INVISIBLE
        viewBinding.btnProsseguir.setOnClickListener {
            val validateRegexPassword = RegexUtil.pattern_validate(viewBinding.senha.text.toString(),regexSenha,viewBinding.senha,getString(R.string.password_invalid_error))
            val validateRegexEmail = RegexUtil.pattern_validate(viewBinding.email.text.toString(),regexEmail,viewBinding.email, getString(R.string.email_invalid_error))
            val validatePassword = ValidateUtil.validate(viewBinding.senha)
            val validateEmail = ValidateUtil.validate(viewBinding.email)
            val validatePasswordTwo = ValidateUtil.validate(viewBinding.senhaConfirmar)

            if(validateRegexPassword && validateRegexEmail){
                if(validatePassword && validateEmail && validatePasswordTwo){
                    if(viewBinding.senhaConfirmar.text.toString() != viewBinding.senha.text.toString()){
                        viewBinding.senhaConfirmar.error = getString(R.string.passwordTwo_invalid_error)
                    }else{
                        val apiService = usuarioService()
                        val clientInfo = ClientInfo(
                            email = viewBinding.email.text.toString(),
                        )
                        viewBinding.progressLoading.visibility = View.VISIBLE
                        apiService.verifyEmail(clientInfo){ status: Int?, clientInfo: ClientInfo? ->
                            viewBinding.progressLoading.isIndeterminate = true

                            if (status != 200) {
                                viewBinding.progressLoading.visibility = View.INVISIBLE
                                viewBinding.progressLoading.isIndeterminate = false
                                Snackbar.make(viewBinding.Layout, R.string.email_ja_existe, Snackbar.LENGTH_LONG ).show()
                            } else {
                                viewBinding.progressLoading.isIndeterminate = false
                                sendData(viewBinding.email.text.toString(), viewBinding.senha.text.toString())
//                                val cadastroPrincipal = Intent(this, CadastroActivity::class.java)
//                                startActivity(cadastroPrincipal)
                            }
                        }
                    }
                }
            }
        }
    }
}