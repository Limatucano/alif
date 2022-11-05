package com.tcc.alif.view.base

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.tcc.alif.R
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

        minSearchSize = typeArray.getInt(
            R.styleable.SearchField_minSearchSize,
            DEFAULT_MIN_SEARCH_SIZE
        )
    }

    fun setOnQueryTextListener(
        onTextChanged: (text: String) -> Unit,
        onSubmitClicked: (text: String) -> Unit
    ) = binding.run{

        searchLayout.setEndIconOnClickListener {
            if(this.searchText.text.size() >= DEFAULT_MIN_SEARCH_SIZE){
                onSubmitClicked.invoke(searchText.text.toString())
            }
        }

        searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(s.size() >= DEFAULT_MIN_SEARCH_SIZE){
                    onTextChanged.invoke(s.toString())
                }
            }
        })
    }

    companion object{
        const val DEFAULT_MIN_SEARCH_SIZE = 3
    }

}