package com.tcc.alif.view.ui

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tcc.alif.R

abstract class BaseBottomSheetDialog(
    context: Context,
    @StyleRes styleRes: Int = R.style.DialogStyle
) : BottomSheetDialog(
    context,
    styleRes
) {

    override fun show() {
        val context = context
        if(context !is Activity || !context.isFinishing || !context.isDestroyed){
            super.show()
        }else{
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setParams()
    }

    protected fun setParams(){
        val params = WindowManager.LayoutParams(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        params.copyFrom(window?.attributes)
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        window?.attributes = params
    }
}