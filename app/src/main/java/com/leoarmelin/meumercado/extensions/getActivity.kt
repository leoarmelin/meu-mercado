package com.leoarmelin.meumercado.extensions

import android.content.Context
import android.content.ContextWrapper
import com.google.accompanist.pager.ExperimentalPagerApi
import com.leoarmelin.meumercado.MainActivity

@ExperimentalPagerApi
fun Context.getActivity(): MainActivity? = when (this) {
    is MainActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}