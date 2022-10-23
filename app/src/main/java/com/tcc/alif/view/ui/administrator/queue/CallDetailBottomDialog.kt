package com.tcc.alif.view.ui.administrator.queue

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tcc.alif.R
import com.tcc.alif.data.model.Call
import com.tcc.alif.databinding.DialogCallDetailBinding
import com.tcc.alif.view.ui.BaseBottomSheetDialog

class CallDetailBottomDialog(
    context: Context,
    private val call: Call,
    private val action: ((CallsIntent) -> Unit)? = null,
    private val isCancelable: Boolean = true
) : BaseBottomSheetDialog(
    context = context,
    styleRes = R.style.DialogStyle
) {
    private lateinit var binding: DialogCallDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCallDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setCancelable(isCancelable)
        setOnShowListener {
            val sheet = findViewById<FrameLayout>(R.id.design_bottom_sheet)
            sheet?.let { BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED }
        }
    }

    private fun setView() = binding.run{
        finishCall.setOnClickListener {
            action?.invoke(CallsIntent.SetToFinish(call))
            dismiss()
        }
        inProgressCall.setOnClickListener {
            action?.invoke(CallsIntent.SetInProgress(call))
            dismiss()
        }
        cancelAction.setOnClickListener {
            dismiss()
        }
    }
}