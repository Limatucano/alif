package com.tcc.alif.util

import com.google.android.material.textfield.TextInputEditText

object ValidateUtil {
    /**
     * the function to validate required fields
     * @param TextInputEditText
     * @return error field
     */
    fun validate(campo: TextInputEditText){
        if(campo.text?.length == 0){
            return campo.setError("Campo Vazio")
        }
    }
    /**
     * the function clear validation fields
     * @param TextInputEditText
     * @return error field
     */
     fun clear_validate(campo: TextInputEditText){
        if(campo.text?.length == 0) {
            return campo.setError(null)
        }
    }
}