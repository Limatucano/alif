package com.tcc.alif.data.util

import android.widget.EditText

object MaskUtils {

    const val CELLPHONE_MASK = "(##) #####-####"
    const val CPF_MASK = "###.###.###-##"
    const val CNPJ_MASK = "##.###.###/####-##"
    const val DATE_MASK = "##/##/####"
    const val ZIPCODE_MASK = "#####-###"

    fun EditText.setZipCodeMask() = Mask.mask(
        mask = ZIPCODE_MASK,
        editText = this
    )

    fun EditText.setCellphoneMask() = Mask.mask(
        mask = CELLPHONE_MASK,
        editText = this
    )

    fun EditText.setCpfMask() = Mask.mask(
        mask = CPF_MASK,
        editText = this
    )

    fun EditText.setCnpjMask() = Mask.mask(
        mask = CNPJ_MASK,
        editText = this
    )

    fun EditText.setDateMask() = Mask.mask(
        mask = DATE_MASK,
        editText = this
    )

}