package com.tcc.alif.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityFirstCadstroBinding
import com.tcc.alif.model.util.RegexUtil
import com.tcc.alif.model.util.ValidateUtil

class FirstCadstroActivity : AppCompatActivity() {

    private val viewBinding: ActivityFirstCadstroBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_cadstro)
        setupListeners()
    }

    private fun setupListeners(){
        val regexSenha = RegexUtil.passwordRegex()
        val regexEmail = RegexUtil.emailRegex()
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
                        val cadastroPrincipal = Intent(this, CadastroActivity::class.java)
                        startActivity(cadastroPrincipal)
                    }
                }
            }
        }
    }
}