package com.tcc.alif.data.util

import android.view.View
import android.view.animation.AnimationUtils

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