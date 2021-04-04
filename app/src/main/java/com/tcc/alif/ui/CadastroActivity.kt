package com.tcc.alif.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText
import com.tcc.alif.R


class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        //elementos
        val radio_lojista    = findViewById<RadioButton>(R.id.lojista)
        val radio_cliente    = findViewById<RadioButton>(R.id.cliente)
        val cadastropessoa   = findViewById<CardView>(R.id.cadastropessoa)
        val cadastrolojista  = findViewById<CardView>(R.id.cadastrolojista)
        val btn_cadastrar    = findViewById<Button>(R.id.btn_cadastrar)
        //elementos pessoa juridica
        val nome_empresa     = findViewById<TextInputEditText>(R.id.nome_empresa)
        val cnpj_cpf         = findViewById<TextInputEditText>(R.id.cnpj_cpf)
        val senha_juridico   = findViewById<TextInputEditText>(R.id.senha_juridico)
        val celular_juridico = findViewById<TextInputEditText>(R.id.celular_juridico)
        //elementos pessoa fisica
        val nome             = findViewById<TextInputEditText>(R.id.nome)
        val cpf              = findViewById<TextInputEditText>(R.id.cpf)
        val senha            = findViewById<TextInputEditText>(R.id.senha)
        val celular          = findViewById<TextInputEditText>(R.id.celular)
        val data_nascimento  = findViewById<TextInputEditText>(R.id.data_nascimento)

        val regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&+-])[A-Za-z\\d@\$!%*#?&+-]{8,}\$".toRegex()


        btn_cadastrar.setOnClickListener{
            //validar campos conforme card que esta visivel no momento
            if(radio_lojista.isChecked){
                password_valid(senha_juridico.text.toString(),regex,senha_juridico)
                validate(nome_empresa)
                validate(cnpj_cpf)
                validate(senha_juridico)
                validate(celular_juridico)
            }
            if(radio_cliente.isChecked){
                password_valid(senha.text.toString(),regex,senha)
                validate(nome)
                validate(cpf)
                validate(senha)
                validate(celular)
                validate(data_nascimento)
            }

        }
        radio_lojista.setOnClickListener{
            if(radio_lojista.isChecked){
                cadastrolojista.visibility = View.VISIBLE
                cadastropessoa.visibility = View.INVISIBLE
                clear_validate(nome)
                clear_validate(cpf)
                clear_validate(senha)
                clear_validate(celular)
                clear_validate(data_nascimento)
                //limpa os campos do outro card
                nome.text?.clear()
                cpf.text?.clear()
                senha.text?.clear()
                celular.text?.clear()
                data_nascimento.text?.clear()
            }

        }
        radio_cliente.setOnClickListener{
            if(radio_cliente.isChecked){
                cadastropessoa.visibility = View.VISIBLE
                cadastrolojista.visibility = View.INVISIBLE
                clear_validate(nome_empresa)
                clear_validate(cnpj_cpf)
                clear_validate(senha_juridico)
                clear_validate(celular_juridico)
                //limpa os campos do outro card
                nome_empresa.text?.clear()
                cnpj_cpf.text?.clear()
                senha_juridico.text?.clear()
                celular_juridico.text?.clear()

            }
        }

    }
    //função para validar a senha, obrigando deixar ela forte BIRL
    fun password_valid(t: String, regex: Regex, campo: TextInputEditText ){
        if(!regex.containsMatchIn(t)){
            campo.setError("Por favor, sua senha precisa ter pelo menos 8 caracteres, sendo eles: números, caracteres especiais(@,$,!,%,*,#,?,&,+,-) e letra maiuscula")
        }
    }
    //função para validar campos obrigatórios
    fun validate(campo: TextInputEditText){
        if(campo.text?.length == 0){
            return campo.setError("Campo Vazio")
        }
    }
    fun clear_validate(campo: TextInputEditText){
        if(campo.text?.length == 0) {
            return campo.setError(null)
        }
    }
}
