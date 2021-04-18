package com.tcc.alif.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.tcc.alif.R

class FirstCadstroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_cadstro)

        val btn_prosseguir  = findViewById<Button>(R.id.btn_prosseguir)
        val senha           = findViewById<TextInputEditText>(R.id.senha)
        val regex           = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&+-])[A-Za-z\\d@\$!%*#?&+-]{8,}\$".toRegex()
        val email           = findViewById<TextInputEditText>(R.id.email)
        val senha_confirmar = findViewById<TextInputEditText>(R.id.senha_confirmar)
        var vSenha: Boolean
        var vEmail: Boolean
        var vSenhaConfirmar : Boolean

        btn_prosseguir.setOnClickListener {
            password_valid(senha.text.toString(),regex,senha)
            vSenha = validate(senha)
            vEmail = validate(email)
            vSenhaConfirmar = validate(senha_confirmar)

            if(!vSenha || !vEmail || !vSenhaConfirmar){

            }else{
                if(senha_confirmar.text.toString() != senha.text.toString()){
                    senha_confirmar.error = "senha inválida, digite a mesma senha do campo anterior"
                }else{
                    val cadastro_principal = Intent(this, CadastroActivity::class.java)
                    startActivity(cadastro_principal)
                }
            }
        }
    }
    //função para validar a senha, obrigando deixar ela forte BIRL
    fun password_valid(t: String, regex: Regex, campo: TextInputEditText){
        if(!regex.containsMatchIn(t)){
            campo.error = "Por favor, sua senha precisa ter pelo menos 8 caracteres, sendo eles: números, caracteres especiais(@,$,!,%,*,#,?,&,+,-) e letra maiuscula"
        }
    }
    //função para validar campos obrigatórios
    fun validate(campo: TextInputEditText): Boolean{
        if(campo.text?.length == 0){
            campo.error = "Campo inválido, é obrigatório preencher"
            return false
        }
        return true
    }
}