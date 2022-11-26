package com.tcc.alif.view.base

import android.content.Context
import android.renderscript.ScriptGroup
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.tcc.alif.R
import com.tcc.alif.data.util.Mask
import com.tcc.alif.data.util.MaskUtils
import com.tcc.alif.data.util.MaskUtils.setCpfMask
import com.tcc.alif.data.util.size
import com.tcc.alif.databinding.SearchFieldBinding

class SearchField @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
): ConstraintLayout(
    context,
    attrs,
    defStyleAttr
) {

    private var binding: SearchFieldBinding
    private var minSearchSize: Int = DEFAULT_MIN_SEARCH_SIZE
    private var hint: String? = ""
    private var inputType: String? = ""

    init {
        binding = SearchFieldBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        val typeArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SearchField,
            0,
            0
        )

        inputType = typeArray.getString(R.styleable.SearchField_type)

        minSearchSize = typeArray.getInt(
            R.styleable.SearchField_minSearchSize,
            DEFAULT_MIN_SEARCH_SIZE
        )

        hint = typeArray.getString(R.styleable.SearchField_hint)

        binding.searchLayout.hint = hint.toString()
    }

    fun addTextChangedListener(mask: String) = binding.run{
        searchText.addTextChangedListener(
            Mask.mask(
            mask = mask,
            editText = searchText
        ))
    }

    fun setOnQueryTextListener(
        onTextChanged: (text: String) -> Unit,
        onSubmitClicked: (text: String) -> Unit
    ) = binding.run{

        searchLayout.setEndIconOnClickListener {
            if(searchText.text.toString().length > minSearchSize){
                onSubmitClicked.invoke(searchText.text.toString())
            }
        }

        searchText.inputType = when(inputType){
            "text" -> InputType.TYPE_CLASS_TEXT
            "number" -> InputType.TYPE_CLASS_NUMBER
            else -> InputType.TYPE_CLASS_TEXT
        }

        searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length >= minSearchSize){
                    onTextChanged.invoke(s.toString())
                }
            }
        })
    }

    companion object{
        const val DEFAULT_MIN_SEARCH_SIZE = 0
    }

}