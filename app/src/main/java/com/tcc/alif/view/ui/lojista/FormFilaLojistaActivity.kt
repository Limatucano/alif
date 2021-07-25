package com.tcc.alif.view.ui.lojista

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityFormFilaLojistaBinding
import com.tcc.alif.model.FilaInfo
import com.tcc.alif.model.MessageRequest
import com.tcc.alif.model.RestApiService
import com.tcc.alif.model.util.TimerPickerHelper
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

class FormFilaLojistaActivity : AppCompatActivity(), Serializable{
    private val viewBinding : ActivityFormFilaLojistaBinding by viewBinding()
    lateinit var timePicker : TimerPickerHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        val apiService = RestApiService()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_fila_lojista)

        val fila = intent?.getSerializableExtra("fila") as HashMap<*, *>?

        if(!fila.isNullOrEmpty()){
            viewBinding.nomeFila.setText(fila["nome_da_fila"].toString())
            viewBinding.QuantidadeFila.setText(fila["quantidade_vagas"].toString())
            viewBinding.minutosMedia.setText(fila["tempo_medio"].toString())
            viewBinding.horarioAbertura.setText(fila["horario_abertura"].toString())
            viewBinding.horarioFechamento.setText(fila["horario_fechamento"].toString())
            viewBinding.idFila.text = fila["id_fila"].toString()
            viewBinding.excluirFila.visibility = View.VISIBLE
        }else{
            viewBinding.excluirFila.visibility = View.GONE
        }


        timePicker = TimerPickerHelper(this, true, true)
        viewBinding.horarioAbertura.inputType = InputType.TYPE_NULL
        viewBinding.calendarHorarioAbertura.setOnClickListener {
            showTimePickerDialog(viewBinding.horarioAbertura)
        }
        viewBinding.calendarHorarioFechamento.setOnClickListener {
            showTimePickerDialog(viewBinding.horarioFechamento)
        }
        viewBinding.btnVoltar.setOnClickListener {
            finish()
        }
        viewBinding.excluirFila.setOnClickListener {
            apiService.deleteFila(viewBinding.idFila.text.toString()){ status: Int?, response: MessageRequest? ->
                finish()
            }
        }
        viewBinding.salvarFila.setOnClickListener {
            val preferences = this.getSharedPreferences("LojistaData", Context.MODE_PRIVATE)
            val dataFila = FilaInfo(
                    nome_da_fila = viewBinding.nomeFila.text.toString(),
                    quantidade_vagas = viewBinding.QuantidadeFila.text.toString().toInt(),
                    horario_abertura = viewBinding.horarioAbertura.text.toString(),
                    horario_fechamento = viewBinding.horarioFechamento.text.toString(),
                    id_lojista = preferences.getInt("id_lojista", 0).toString(),
                    tempo_medio = viewBinding.minutosMedia.text.toString(),
            )
            apiService.registerFila(dataFila) { status: Int?, filaData: FilaInfo? ->
                if (status != 201) {
                    Snackbar.make(viewBinding.layout, R.string.erro_cadastrar, Snackbar.LENGTH_LONG).show()
                } else {
                    finish()
                }
            }
        }
    }
    private fun showTimePickerDialog(field: TextInputEditText){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        timePicker.showDialog(hour, minute, object: TimerPickerHelper.Callback{
            override fun onTimeSelected(hourOfDay: Int, minute: Int) {
                val hourStr = hourOfDay.toString().padStart(2,'0')
                val minuteStr = minute.toString().padStart(2,'0')
                val formatted = "${hourStr}:${minuteStr}"
                field.setText(formatted)
            }
        })
    }
}