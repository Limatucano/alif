package com.tcc.alif.view.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import com.tcc.alif.R
import com.tcc.alif.databinding.DialogTwoOptionsBinding
import com.tcc.alif.view.ui.BaseBottomSheetDialog

class TwoOptionsBottomDialog @JvmOverloads constructor(
    context: Context,
    private val saveAction: (() -> Unit)? = null,
    private val cancelAction: (() -> Unit)? = null,
    private val message: String,
    @StringRes private val saveText: Int = R.string.salvar,
    @StringRes private val cancelText: Int = R.string.cancel,
    @StringRes private val title: Int = R.string.warning,
    isCancelable: Boolean = true
) : BaseBottomSheetDialog(
    context = context,
    styleRes = R.style.DialogStyle,
    isCancelable = isCancelable
) {
    private lateinit var binding: DialogTwoOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogTwoOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setListener()
    }
    private fun setView(){
        binding.title.text = context.getString(title)
        binding.message.text = message
        binding.save.text = context.getString(saveText)
        binding.cancel.text = context.getString(cancelText)
    }

    private fun setListener() = binding.run{
        save.setOnClickListener {
            saveAction?.invoke()
            dismiss()
        }
        cancel.setOnClickListener {
            cancelAction?.invoke()
            dismiss()
        }
    }
}