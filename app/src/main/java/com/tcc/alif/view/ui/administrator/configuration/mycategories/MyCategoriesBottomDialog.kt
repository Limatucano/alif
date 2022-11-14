package com.tcc.alif.view.ui.administrator.configuration.mycategories

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tcc.alif.R
import com.tcc.alif.data.model.CategoryResponse
import com.tcc.alif.databinding.DialogCategoriesBinding
import com.tcc.alif.view.ui.BaseBottomSheetDialog

class MyCategoriesBottomDialog(
    context: Context,
    private val category: CategoryResponse,
    private val action: ((MyCategoriesIntent) -> Unit)? = null,
    private val isCancelable: Boolean = true
) : BaseBottomSheetDialog(
    context = context,
    styleRes = R.style.DialogStyle
) {
    private lateinit var binding: DialogCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setCancelable(isCancelable)
        setOnShowListener {
            val sheet = findViewById<FrameLayout>(R.id.design_bottom_sheet)
            sheet?.let { BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED }
        }
    }

    private fun setView() = binding.run{
        editCategory.setOnClickListener {
            action?.invoke(MyCategoriesIntent.EditCategory(category))
            dismiss()
        }
        deleteCategory.setOnClickListener {
            action?.invoke(MyCategoriesIntent.DeleteCategory(category))
            dismiss()
        }
        cancelAction.setOnClickListener {
            dismiss()
        }
    }
}