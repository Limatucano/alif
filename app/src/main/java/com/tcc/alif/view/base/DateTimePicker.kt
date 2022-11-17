package com.tcc.alif.view.base

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import com.tcc.alif.R
import com.tcc.alif.data.util.setVisible
import com.tcc.alif.databinding.DateTimePickerViewBinding
import com.tcc.alif.databinding.DateTimePickerViewDialogBinding
import java.util.*

class DateTimePicker @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
): ConstraintLayout(
    context,
    attrs,
    defStyleAttr
){
    private var dialog: Dialog
    private var hint: String? = ""
    private var timeIsEnabled : Boolean = true
    private var dateIsEnabled : Boolean = true

    private var binding: DateTimePickerViewBinding = DateTimePickerViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )
    private var dialogBinding: DateTimePickerViewDialogBinding = DateTimePickerViewDialogBinding.inflate(
        LayoutInflater.from(context),
        this,
        false
    )

    var text : String = ""
        set(value){
            field = value
            binding.editText.setText(value)
        }

    private var listener : ((
        calendar: Calendar,
        timeIsEnabled: Boolean
    ) -> Unit)? = null

    private val typeArray  = context.obtainStyledAttributes(
        attrs,
        R.styleable.DateTimePicker,
        0,
        0
    )

    init {
        setupView()
        dialogListener()
        dialog = Dialog(context, R.style.Datepicker)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(dialogBinding.root)
    }

    fun setDateSelected(listener: (Calendar, Boolean) -> Unit){
        this.listener = listener
    }

    private fun dialogListener(){
        dialogBinding.timePicker.setIs24HourView(true)

        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.okButton.setOnClickListener {
            val (hour, minute) = dialogBinding.timePicker.run {
                Pair(hour,minute)
            }

            dialogBinding.datePicker.run {
                val dateSelected = Calendar.getInstance()

                if(timeIsEnabled){
                    dateSelected.set(Calendar.MINUTE, minute)
                    dateSelected.set(Calendar.HOUR_OF_DAY, hour)
                    dateSelected.set(Calendar.SECOND, 0)
                }
                if(dateIsEnabled){
                    dateSelected.set(Calendar.YEAR, year)
                    dateSelected.set(Calendar.MONTH, month)
                    dateSelected.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                listener?.invoke(
                    dateSelected,
                    timeIsEnabled
                )
                dialog.dismiss()
            }

        }

        dialogBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.dateRadio -> {
                    dialogBinding.timePicker.setVisible(false)
                    dialogBinding.datePicker.setVisible(true)
                }
                R.id.timerRadio -> {
                    dialogBinding.timePicker.setVisible(true)
                    dialogBinding.datePicker.setVisible(false)
                }
            }
        }
    }

    private fun setupView() {
        hint = typeArray.getString(R.styleable.DateTimePicker_datehint)
        timeIsEnabled = typeArray.getBoolean(R.styleable.DateTimePicker_timeIsEnabled, true)
        dateIsEnabled = typeArray.getBoolean(R.styleable.DateTimePicker_dateIsEnabled, true)

        if(!timeIsEnabled){
            dialogBinding.timerRadio.setVisible(timeIsEnabled)
            dialogBinding.timePicker.setVisible(timeIsEnabled)
        }

        if(!dateIsEnabled){
            dialogBinding.dateRadio.setVisible(dateIsEnabled)
            dialogBinding.datePicker.setVisible(dateIsEnabled)
        }

        binding.layout.hint = hint.toString()

        binding.editText.setOnClickListener {
            if(dialog.isShowing){
                dialog.dismiss()
            }
            show()
        }
    }

    private fun show() {
        dialog.show()
    }

}