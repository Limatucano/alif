package com.tcc.alif.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityCadastroBinding
import com.tcc.alif.model.ClientInfo
import com.tcc.alif.model.ClientSerializable
import com.tcc.alif.model.RestApiService
import com.tcc.alif.model.util.*


class CadastroActivity : AppCompatActivity() {

    private val viewBinding: ActivityCadastroBinding by viewBinding()
    private var clientSerializable: ClientSerializable? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        setupMasks()
        setupListeners()
        clientSerializable = intent.getSerializableExtra("serialzable") as ClientSerializable?
        var email: String = clientSerializable?.getEmail().toString()
        var senha: String = clientSerializable?.getSenha().toString()
        viewBinding.emailHidden.text = email
        viewBinding.senhaHidden.text = senha

    }
    private fun setupMasks() = with(viewBinding){
        //Mascaras para pessoa juridica
        celularJuridico.addTextChangedListener(MaskUtils.cellphoneMask(celularJuridico))
        cpfLojista.addTextChangedListener(MaskUtils.cpfMask(cpfLojista))
        cnpjLojista.addTextChangedListener(MaskUtils.cnpjMask(cnpjLojista))


        //Mascaras para pessoa fisica
        cpf.addTextChangedListener(MaskUtils.cpfMask(cpf))
        celular.addTextChangedListener(MaskUtils.cellphoneMask(celular))
        dataNascimento.addTextChangedListener(MaskUtils.dateMask(dataNascimento))
    }
    private fun setupListeners() = with(viewBinding){
        btnCadastrar.setOnClickListener{
            //validar campos conforme card que esta visivel no momento
            if(radioLojista.isChecked){
                if(!CPFUtil.myValidateCPF(cpfLojista.text.toString())){
                    cpfLojista.error = getString(R.string.cpf_invalid_error)
                }
                if(!CNPJUtil.myValidateCNPJ(cnpjLojista.text.toString())){
                    cnpjLojista.error = getString(R.string.cnpj_invalid_error)
                }
                ValidateUtil.validate(nomeEmpresa)
                if(radioCpf.isChecked){
                    ValidateUtil.validate(cpfLojista)
                }
                if(radioCnpj.isChecked){
                    ValidateUtil.validate(cnpjLojista)
                }
                ValidateUtil.validate(celularJuridico)
            }
            if(radioCliente.isChecked){
                if(!DateUtil.myValidateDate(dataNascimento.text.toString())){
                    dataNascimento.error = getString(R.string.date_invalid_error)
                }
                if(!CPFUtil.myValidateCPF(cpf.text.toString())){
                    cpf.error = getString(R.string.cpf_invalid_error)
                }

                ValidateUtil.validate(nome)
                ValidateUtil.validate(cpf)
                ValidateUtil.validate(celular)
                ValidateUtil.validate(dataNascimento)

                val apiService = RestApiService()
                val arr = ClientInfo(
                    email = emailHidden.text.toString(),
                    senha = senhaHidden.text.toString(),
                    nome = nome.text.toString(),
                    cpf = cpf.text.toString(),
                    numero_celular = celular.text.toString(),
                    sobrenome = "",
                    nascimento = "1999-05-31",
                    ddd_celular = "",
                )
                apiService.registerClient(arr){ status: Int?, clientInfo: ClientInfo? ->
                    if (status != 201) {
                        Snackbar.make(viewBinding.Layout, R.string.erro_cadastrar, Snackbar.LENGTH_LONG ).show()
                    } else {
                        Log.d("ALIF_API", "Usuario inserido com sucesso")
                        val login = Intent(this@CadastroActivity, MainActivity::class.java)
                        val b = Bundle()
                        b.putSerializable("success", 0)
                        login.putExtras(b)
                        startActivity(login)
                    }
                }
            }

        }
        radioCpf.setOnClickListener{
            if(radioCpf.isChecked){
                inputCnpjLojista.visibility = View.INVISIBLE
                inputCpfLojista.visibility  = View.VISIBLE
                txtCnpjCpf.text   = getString(R.string.cpf)
                ValidateUtil.clear_validate(cnpjLojista)
                //limpa o campo de cnpj
                cnpjLojista.text?.clear()
            }

        }
        radioCnpj.setOnClickListener{
            if(radioCnpj.isChecked){
                inputCnpjLojista.visibility = View.VISIBLE
                inputCpfLojista.visibility  = View.INVISIBLE
                txtCnpjCpf.text   = getString(R.string.cnpj)
                ValidateUtil.clear_validate(cpfLojista)
                //limpa o campo de cpf
                cpfLojista.text?.clear()
            }
        }

        radioLojista.setOnClickListener{
            if(radioLojista.isChecked){
                cadastrolojista.visibility = View.VISIBLE
                cadastropessoa.visibility = View.INVISIBLE
                ValidateUtil.clear_validate(nome)
                ValidateUtil.clear_validate(cpf)
                ValidateUtil.clear_validate(celular)
                ValidateUtil.clear_validate(dataNascimento)
                //limpa os campos do outro card
                nome.text?.clear()
                cpf.text?.clear()
                celular.text?.clear()
                dataNascimento.text?.clear()
            }

        }
        radioCliente.setOnClickListener{
            if(radioCliente.isChecked){
                cadastropessoa.visibility = View.VISIBLE
                cadastrolojista.visibility = View.INVISIBLE
                ValidateUtil.clear_validate(nomeEmpresa)
                ValidateUtil.clear_validate(cpfLojista)
                ValidateUtil.clear_validate(cnpjLojista)
                ValidateUtil.clear_validate(celularJuridico)
                //limpa os campos do outro card
                nomeEmpresa.text?.clear()
                cpfLojista.text?.clear()
                cnpjLojista.text?.clear()
                celularJuridico.text?.clear()

            }
        }
    }


}

