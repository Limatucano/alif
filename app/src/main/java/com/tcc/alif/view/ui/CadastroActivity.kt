package com.tcc.alif.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityCadastroBinding
import com.tcc.alif.model.*
import com.tcc.alif.model.restApiService.lojistaService
import com.tcc.alif.model.restApiService.usuarioService
import com.tcc.alif.model.util.*
import java.text.SimpleDateFormat


class CadastroActivity : AppCompatActivity() {

    private val viewBinding: ActivityCadastroBinding by viewBinding()
    private var clientSerializable: ClientSerializable? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        setupMasks()
        setupListeners()
        clientSerializable = intent.getSerializableExtra("serialzable") as ClientSerializable?
        val email: String = clientSerializable?.getEmail().toString()
        val senha: String = clientSerializable?.getSenha().toString()
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
    private fun setupListeners() = with(viewBinding) {
        viewBinding.progressLoading.visibility = View.INVISIBLE
        btnCadastrar.setOnClickListener {
            //validar campos conforme card que esta visivel no momento
            if (radioLojista.isChecked) {
                if (!CPFUtil.myValidateCPF(cpfLojista.text.toString())) {
                    cpfLojista.error = getString(R.string.cpf_invalid_error)
                }
                if (!CNPJUtil.myValidateCNPJ(cnpjLojista.text.toString())) {
                    cnpjLojista.error = getString(R.string.cnpj_invalid_error)
                }
                ValidateUtil.validate(celularJuridico)
                ValidateUtil.validate(nomeEmpresa)
                val apiService = lojistaService()
                if (radioCpf.isChecked) {
                    ValidateUtil.validate(cpfLojista)

                    if (verifyIfFormLojistaIsValid(celularJuridico,nomeEmpresa,cpfLojista,cnpjLojista)) {
                        val lojistaCPF = LojistaInfo(
                                nome_fantasia = nomeEmpresa.text.toString(),
                                tipo_doc = "CPF",
                                doc = cpfLojista.text.toString(),
                                email = emailHidden.text.toString(),
                                senha = senhaHidden.text.toString()
                        )
                        viewBinding.progressLoading.visibility = View.VISIBLE
                        apiService.registerLojista(lojistaCPF) { status: Int?, lojistaInfo: LojistaInfo? ->
                            viewBinding.progressLoading.isIndeterminate = true
                            if (status != 201) {
                                viewBinding.progressLoading.visibility = View.INVISIBLE
                                viewBinding.progressLoading.isIndeterminate = false
                                Snackbar.make(viewBinding.Layout, R.string.erro_cadastrar, Snackbar.LENGTH_LONG).show()
                            } else {
                                viewBinding.progressLoading.isIndeterminate = false
                                val login = Intent(this@CadastroActivity, ModoActivity::class.java)
                                val b = Bundle()
                                b.putSerializable("success", 0)
                                login.putExtras(b)
                                startActivity(login)
                            }
                        }
                    }
                }
                if (radioCnpj.isChecked) {
                    ValidateUtil.validate(cnpjLojista)
                    if (verifyIfFormLojistaIsValid(celularJuridico,nomeEmpresa,cpfLojista,cnpjLojista)) {
                        val lojistaCNPJ = LojistaInfo(
                                nome_fantasia = nomeEmpresa.text.toString(),
                                tipo_doc = "CNPJ",
                                doc = cnpjLojista.text.toString(),
                                email = emailHidden.text.toString(),
                                senha = senhaHidden.text.toString()
                        )
                        viewBinding.progressLoading.visibility = View.VISIBLE
                        apiService.registerLojista(lojistaCNPJ) { status: Int?, lojistaInfo: LojistaInfo? ->
                            viewBinding.progressLoading.isIndeterminate = true
                            if (status != 201) {
                                viewBinding.progressLoading.visibility = View.INVISIBLE
                                viewBinding.progressLoading.isIndeterminate = false
                                Snackbar.make(viewBinding.Layout, R.string.erro_cadastrar, Snackbar.LENGTH_LONG).show()
                            } else {
                                viewBinding.progressLoading.isIndeterminate = false
                                val login = Intent(this@CadastroActivity, ModoActivity::class.java)
                                val b = Bundle()
                                b.putSerializable("success", 0)
                                login.putExtras(b)
                                startActivity(login)
                            }
                        }
                    }
                }
            }
            if (radioCliente.isChecked) {
                if (!DateUtil.myValidateDate(dataNascimento.text.toString())) {
                    dataNascimento.error = getString(R.string.date_invalid_error)
                }
                if (!CPFUtil.myValidateCPF(cpf.text.toString())) {
                    cpf.error = getString(R.string.cpf_invalid_error)
                }

                val formClienteIsValid = DateUtil.myValidateDate(dataNascimento.text.toString()) && CPFUtil.myValidateCPF(cpf.text.toString()) && ValidateUtil.validate(nome) && ValidateUtil.validate(cpf) && ValidateUtil.validate(celular) && ValidateUtil.validate(dataNascimento)

                if (formClienteIsValid) {
                    val apiServiceUsuario = usuarioService()
                    val nascimentoFormatado = formatDate(dataNascimento.text.toString(),"dd/mm/yyyy","yyyy-mm-dd")

                    val clientData = ClientInfo(
                            email = emailHidden.text.toString(),
                            senha = senhaHidden.text.toString(),
                            nome = nome.text.toString(),
                            nascimento = nascimentoFormatado,
                            cpf = cpf.text.toString(),
                            numero_celular = celular.text.toString(),
                            sobrenome = ""
                    )
                    viewBinding.progressLoading.visibility = View.VISIBLE
                    apiServiceUsuario.registerClient(clientData) { status: Int?, clientInfo: MessageRequest? ->
                        Log.d("TESTE",status.toString())
                        if (status != 201) {
                            viewBinding.progressLoading.visibility = View.INVISIBLE
                            Snackbar.make(viewBinding.Layout, R.string.erro_cadastrar, Snackbar.LENGTH_LONG).show()
                        } else {
                            val login = Intent(this@CadastroActivity, ModoActivity::class.java)
                            val b = Bundle()
                            b.putSerializable("success", 0)
                            login.putExtras(b)
                            startActivity(login)
                        }
                    }
                }
            }
        }
            radioCpf.setOnClickListener {
                    inputCnpjLojista.visibility = View.INVISIBLE
                    inputCpfLojista.visibility = View.VISIBLE
                    txtCnpjCpf.text = getString(R.string.cpf)
                    ValidateUtil.clear_validate(cnpjLojista)
                    //limpa o campo de cnpj
                    cnpjLojista.text?.clear()
            }
            radioCnpj.setOnClickListener {
                    inputCnpjLojista.visibility = View.VISIBLE
                    inputCpfLojista.visibility = View.INVISIBLE
                    txtCnpjCpf.text = getString(R.string.cnpj)
                    ValidateUtil.clear_validate(cpfLojista)
                    //limpa o campo de cpf
                    cpfLojista.text?.clear()
            }

            radioLojista.setOnClickListener {
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
            radioCliente.setOnClickListener {
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
    fun verifyIfFormLojistaIsValid(celularJuridico : TextInputEditText, nomeEmpresa : TextInputEditText, cpfLojista: TextInputEditText, cnpjLojista: TextInputEditText) : Boolean{
        val formLojistaIsValid = CPFUtil.myValidateCPF(cpfLojista.text.toString()) && CNPJUtil.myValidateCNPJ(cnpjLojista.text.toString()) && ValidateUtil.validate(celularJuridico) && ValidateUtil.validate(nomeEmpresa) && (ValidateUtil.validate(cpfLojista) || ValidateUtil.validate(cnpjLojista))
        return formLojistaIsValid
    }

    fun formatDate(
        date: String?,
        initDateFormat: String?,
        endDateFormat: String?
    ): String? {
        val initDate = SimpleDateFormat(initDateFormat).parse(date)
        val formatter = SimpleDateFormat(endDateFormat)
        return formatter.format(initDate)
    }

}

