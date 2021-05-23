package com.tcc.alif.util

import android.widget.EditText

object MaskUtils {

    private const val CELLPHONE_MASK = "(##) #####-####"
    private const val CPF_MASK = "###.###.###-##"
    private const val CNPJ_MASK = "##.###.###/####-##"
    private const val DATE_MASK = "##/##/####"

    fun cellphoneMask(editText: EditText) = Mask.mask(CELLPHONE_MASK, editText)

    fun cpfMask(editText: EditText) = Mask.mask(CPF_MASK, editText)

    fun cnpjMask(editText: EditText) = Mask.mask(CNPJ_MASK, editText)

    fun dateMask(editText: EditText) = Mask.mask(DATE_MASK, editText)

}