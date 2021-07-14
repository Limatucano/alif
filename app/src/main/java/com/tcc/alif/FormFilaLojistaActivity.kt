package com.tcc.alif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputEditText
import com.tcc.alif.databinding.ActivityFormFilaLojistaBinding
import com.tcc.alif.model.util.TimerPickerHelper
import java.util.*

class FormFilaLojistaActivity : AppCompatActivity(){
    private val viewBinding : ActivityFormFilaLojistaBinding by viewBinding()
    lateinit var timePicker : TimerPickerHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_fila_lojista)
        timePicker = TimerPickerHelper(this, true, true)
        viewBinding.horarioAbertura.inputType = InputType.TYPE_NULL
        viewBinding.calendarHorarioAbertura.setOnClickListener {
            showTimePickerDialog(viewBinding.horarioAbertura)
        }
        viewBinding.calendarHorarioFechamento.setOnClickListener {
            showTimePickerDialog(viewBinding.horarioFechamento)
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