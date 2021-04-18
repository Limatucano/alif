package com.tcc.alif.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tcc.alif.R
import com.tcc.alif.util.CNPJUtil
import com.tcc.alif.util.CPFUtil
import com.tcc.alif.util.DateUtil
import com.tcc.alif.util.Mask


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
        val cpf_lojista      = findViewById<TextInputEditText>(R.id.cpf_lojista)
        val cnpj_lojista     = findViewById<TextInputEditText>(R.id.cnpj_lojista)
        val senha_juridico   = findViewById<TextInputEditText>(R.id.senha_juridico)
        val celular_juridico = findViewById<TextInputEditText>(R.id.celular_juridico)
        val radio_cpf        = findViewById<RadioButton>(R.id.radio_cpf)
        val radio_cnpj       = findViewById<RadioButton>(R.id.radio_cnpj)
        val txt_cnpj_cpf     = findViewById<TextView>(R.id.txt_cnpj_cpf)
        val input_cpf_lojista  = findViewById<TextInputLayout>(R.id.input_cpf_lojista)
        val input_cnpj_lojista = findViewById<TextInputLayout>(R.id.input_cnpj_lojista)
        //elementos pessoa fisica
        val nome             = findViewById<TextInputEditText>(R.id.nome)
        val cpf              = findViewById<TextInputEditText>(R.id.cpf)
        val senha            = findViewById<TextInputEditText>(R.id.senha)
        val celular          = findViewById<TextInputEditText>(R.id.celular)
        val data_nascimento  = findViewById<TextInputEditText>(R.id.data_nascimento)
        val regex            = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&+-])[A-Za-z\\d@\$!%*#?&+-]{8,}\$".toRegex()

        //Mascaras para pessoa juridica
        celular_juridico.addTextChangedListener(Mask.mask("(##) #####-####", celular_juridico))
        cpf_lojista.addTextChangedListener(Mask.mask("###.###.###-##", cpf_lojista))
        cnpj_lojista.addTextChangedListener(Mask.mask("##.###.###/####-##", cnpj_lojista))


        //Mascaras para pessoa fisica
        cpf.addTextChangedListener(Mask.mask("###.###.###-##", cpf))
        celular.addTextChangedListener(Mask.mask("(##) #####-####",celular))
        data_nascimento.addTextChangedListener(Mask.mask("##/##/####",data_nascimento))

        btn_cadastrar.setOnClickListener{
            //validar campos conforme card que esta visivel no momento
            if(radio_lojista.isChecked){
                if(!CPFUtil.myValidateCPF(cpf_lojista.text.toString())){
                    cpf_lojista.error = "CPF Inválido, digite um CPF válido para prosseguir"
                }
                if(!CNPJUtil.myValidateCNPJ(cnpj_lojista.text.toString())){
                    cnpj_lojista.error = "CNPJ Inválido, digite um CNPJ válido para prosseguir"
                }
                password_valid(senha_juridico.text.toString(),regex,senha_juridico)
                validate(nome_empresa)
                if(radio_cpf.isChecked){
                    validate(cpf_lojista)
                }
                if(radio_cnpj.isChecked){
                    validate(cnpj_lojista)
                }
                validate(senha_juridico)
                validate(celular_juridico)
            }
            if(radio_cliente.isChecked){
                if(!DateUtil.myValidateDate(data_nascimento.text.toString())){
                    data_nascimento.error = "Data de Nascimento inválida"
                }
                if(!CPFUtil.myValidateCPF(cpf.text.toString())){
                    cpf.error = "CPF Inválido, digite um CPF válido para prosseguir"
                }
                password_valid(senha.text.toString(),regex,senha)
                validate(nome)
                validate(cpf)
                validate(senha)
                validate(celular)
                validate(data_nascimento)
            }

        }
        radio_cpf.setOnClickListener{
            if(radio_cpf.isChecked){
                input_cnpj_lojista.visibility = View.INVISIBLE
                input_cpf_lojista.visibility  = View.VISIBLE
                txt_cnpj_cpf.text   = "CPF"
                clear_validate(cnpj_lojista)
                //limpa o campo de cnpj
                cnpj_lojista.text?.clear()
            }
        }
        radio_cnpj.setOnClickListener{
            if(radio_cnpj.isChecked){
                input_cnpj_lojista.visibility = View.VISIBLE
                input_cpf_lojista.visibility  = View.INVISIBLE
                txt_cnpj_cpf.text   = "CNPJ"
                clear_validate(cpf_lojista)
                //limpa o campo de cpf
                cpf_lojista.text?.clear()
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
                clear_validate(cpf_lojista)
                clear_validate(cnpj_lojista)
                clear_validate(senha_juridico)
                clear_validate(celular_juridico)
                //limpa os campos do outro card
                nome_empresa.text?.clear()
                cpf_lojista.text?.clear()
                cnpj_lojista.text?.clear()
                senha_juridico.text?.clear()
                celular_juridico.text?.clear()

            }
        }

    }
    //função para validar a senha, obrigando deixar ela forte BIRL
    fun password_valid(t: String, regex: Regex, campo: TextInputEditText ){
        if(!regex.containsMatchIn(t)){
            campo.error = "Por favor, sua senha precisa ter pelo menos 8 caracteres, sendo eles: números, caracteres especiais(@,$,!,%,*,#,?,&,+,-) e letra maiuscula"
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

