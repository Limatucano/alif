package com.tcc.alif.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputEditText
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityCadastroBinding
import com.tcc.alif.util.*


class CadastroActivity : AppCompatActivity() {

    private val viewBinding: ActivityCadastroBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        setupMasks()
        setupListeners()

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
                    cpfLojista.error = "CPF Inválido, digite um CPF válido para prosseguir"
                }
                if(!CNPJUtil.myValidateCNPJ(cnpjLojista.text.toString())){
                    cnpjLojista.error = "CNPJ Inválido, digite um CNPJ válido para prosseguir"
                }
                validate(nomeEmpresa)
                if(radioCpf.isChecked){
                    validate(cpfLojista)
                }
                if(radioCnpj.isChecked){
                    validate(cnpjLojista)
                }
                validate(celularJuridico)
            }
            if(radioCliente.isChecked){
                if(!DateUtil.myValidateDate(dataNascimento.text.toString())){
                    dataNascimento.error = "Data de Nascimento inválida"
                }
                if(!CPFUtil.myValidateCPF(cpf.text.toString())){
                    cpf.error = "CPF Inválido, digite um CPF válido para prosseguir"
                }

                validate(nome)
                validate(cpf)
                validate(celular)
                validate(dataNascimento)
            }

        }
        radioCpf.setOnClickListener{
            if(radioCpf.isChecked){
                inputCnpjLojista.visibility = View.INVISIBLE
                inputCpfLojista.visibility  = View.VISIBLE
                txtCnpjCpf.text   = "CPF"
                clear_validate(cnpjLojista)
                //limpa o campo de cnpj
                cnpjLojista.text?.clear()
            }
        }
        radioCnpj.setOnClickListener{
            if(radioCnpj.isChecked){
                inputCnpjLojista.visibility = View.VISIBLE
                inputCpfLojista.visibility  = View.INVISIBLE
                txtCnpjCpf.text   = "CNPJ"
                clear_validate(cpfLojista)
                //limpa o campo de cpf
                cpfLojista.text?.clear()
            }
        }

        radioLojista.setOnClickListener{
            if(radioLojista.isChecked){
                cadastrolojista.visibility = View.VISIBLE
                cadastropessoa.visibility = View.INVISIBLE
                clear_validate(nome)
                clear_validate(cpf)
                clear_validate(celular)
                clear_validate(dataNascimento)
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
                clear_validate(nomeEmpresa)
                clear_validate(cpfLojista)
                clear_validate(cnpjLojista)
                clear_validate(celularJuridico)
                //limpa os campos do outro card
                nomeEmpresa.text?.clear()
                cpfLojista.text?.clear()
                cnpjLojista.text?.clear()
                celularJuridico.text?.clear()

            }
        }
    }

    //função para validar campos obrigatórios
    private fun validate(campo: TextInputEditText){
        if(campo.text?.length == 0){
            return campo.setError("Campo Vazio")
        }
    }
    private fun clear_validate(campo: TextInputEditText){
        if(campo.text?.length == 0) {
            return campo.setError(null)
        }
    }
}

