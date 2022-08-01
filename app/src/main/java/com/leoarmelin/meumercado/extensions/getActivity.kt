package com.leoarmelin.meumercado.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.compose.animation.ExperimentalAnimationApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.leoarmelin.meumercado.MainActivity

@ExperimentalAnimationApi
@ExperimentalPagerApi
fun Context.getActivity(): MainActivity? = when (this) {
    is MainActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}