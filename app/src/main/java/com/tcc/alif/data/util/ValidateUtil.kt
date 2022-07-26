package com.tcc.alif.data.util

import com.google.android.material.textfield.TextInputEditText

object ValidateUtil {
    /**
     * the function to validate required fields
     * @param TextInputEditText
     * @return Boolean
     */
    fun validate(campo: TextInputEditText): Boolean {
        if(campo.text.isNullOrBlank() || campo.text?.length == 0 || campo.text.isNullOrEmpty()){
            campo.error = "Campo Vazio"
            return false
        }
        return true
    }
    /**
     * the function clear validation fields
     * @param TextInputEditText
     * @return error field
     */
     fun clear_validate(campo: TextInputEditText){
        if(campo.text.isNullOrBlank() || campo.text?.length == 0 || campo.text.isNullOrEmpty()) {
            return campo.setError(null)
        }
    }
}