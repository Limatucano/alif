package com.tcc.alif.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.tcc.alif.R

class FirstCadstroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_cadstro)

        val btn_prosseguir  = findViewById<Button>(R.id.btn_prosseguir)
        val senha           = findViewById<TextInputEditText>(R.id.senha)
        val regex_senha     = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&+-])[A-Za-z\\d@\$!%*#?&+-]{8,}\$".toRegex()
        val regex_email     = "[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?".toRegex()
        val email           = findViewById<TextInputEditText>(R.id.email)
        val senha_confirmar = findViewById<TextInputEditText>(R.id.senha_confirmar)

        btn_prosseguir.setOnClickListener {
            pattern_validate(senha.text.toString(),regex_senha,senha,"Por favor, sua senha precisa ter pelo menos 8 caracteres, sendo eles: números, caracteres especiais(@,\$,!,%,*,#,?,&,+,-) e letra maiuscula")
            pattern_validate(email.text.toString(),regex_email,email, "Digite um email válido")
            val vSenha = validate(senha)
            val vEmail = validate(email)
            val vSenhaConfirmar = validate(senha_confirmar)

            if(vSenha || vEmail || vSenhaConfirmar){
                if(senha_confirmar.text.toString() != senha.text.toString()){
                    senha_confirmar.error = "senha inválida, digite a mesma senha do campo anterior"
                }else{
                    val cadastroPrincipal = Intent(this, CadastroActivity::class.java)
                    startActivity(cadastroPrincipal)
                }
            }
        }
    }
    //função para validar usando regex
    fun pattern_validate(t: String, regex: Regex, campo: TextInputEditText,error:String){
        if(!regex.containsMatchIn(t)){
            campo.error = error
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