package com.tcc.alif.view.base

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

class SearchField @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
): TextInputLayout(
    context,
    attrs,
    defStyleAttr
) {

}