package com.tcc.alif.util

import android.widget.EditText

object MaskUtils {

    private const val CELLPHONE_MASK = "(##) #####-####"
    private const val CPF_MASK = "###.###.###-##"
    private const val CNPJ_MASK = "##.###.###/####-##"
    private const val DATE_MASK = "##/##/####"

    /**
     * the function to set cellphone mask
     * @param EditText
     * @return void
     */
    fun cellphoneMask(editText: EditText) = Mask.mask(CELLPHONE_MASK, editText)
    /**
     * the function to set cpf mask
     * @param EditText
     * @return void
     */
    fun cpfMask(editText: EditText) = Mask.mask(CPF_MASK, editText)
    /**
     * the function to set cnpj mask
     * @param EditText
     * @return void
     */
    fun cnpjMask(editText: EditText) = Mask.mask(CNPJ_MASK, editText)
    /**
     * the function to set date mask
     * @param EditText
     * @return void
     */
    fun dateMask(editText: EditText) = Mask.mask(DATE_MASK, editText)

}