package com.tcc.alif.util

import com.google.android.material.textfield.TextInputEditText

object RegexUtil {
    fun passwordRegex() = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&+-])[A-Za-z\\d@\$!%*#?&+-]{8,}\$".toRegex()
    fun emailRegex() = "[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?".toRegex()

    /**
     * the function to validate pattern fields
     * @param String, Regex, TextInputEditText, String
     * @return Boolean
     */
    fun pattern_validate(t: String, regex: Regex, campo: TextInputEditText, error:String): Boolean {
        if(!regex.containsMatchIn(t)){
             campo.error = error
            return false
        }
        return true
    }
}