package com.tcc.alif.data.util

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


fun List<EditText>.validateFields() : Boolean{
    this.forEach {
        if(it.text.isEmpty() || it.text.isBlank()){
            return false
        }
    }
    return true
}

fun View.animateVisibility(
    visible : Boolean,
    useInvisible : Boolean = false,
    inAnimationId : Int?,
    outAnimationId : Int?
){
    when{
        visibility == View.VISIBLE && !visible -> {
            outAnimationId?.let {
                translate(it)
                post{
                    setVisible(visible,useInvisible)
                }
            } ?: setVisible(visible, useInvisible)
        }
        visibility != View.VISIBLE && visible -> {
            setVisible(visible, useInvisible)
            inAnimationId?.let { translate(it) }
        }
    }
}

fun View.setVisible(
    visible: Boolean,
    useInvisible: Boolean = false
){
    visibility = when{
        visible -> View.VISIBLE
        useInvisible -> View.INVISIBLE
        else -> View.GONE
    }
}

fun View.translate(
    animId : Int,
    duration : Long? = null
){
    val anim = AnimationUtils.loadAnimation(this.context,animId)
    if(duration != null) anim.duration = duration
    startAnimation(anim)
}

fun RecyclerView.setGridLayout(
    context : Context,
    spanCount : Int
){
    this.layoutManager = GridLayoutManager(
        context,
        spanCount
    )
}

fun RecyclerView.setLinearLayout(
    context : Context,
    orientation : Int,
    reverseLayout : Boolean,
    withItemDecoration : Boolean = false
){
    this.layoutManager = LinearLayoutManager(
        context,
        orientation,
        reverseLayout
    )
    if(withItemDecoration){
        this.addItemDecoration(
            DividerItemDecoration(
                context,
                orientation
            )
        )
    }
}