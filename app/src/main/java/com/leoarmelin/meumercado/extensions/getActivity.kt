package com.leoarmelin.meumercado.extensions

import android.content.Context
import android.content.ContextWrapper
import com.leoarmelin.meumercado.MainActivity

fun Context.getActivity(): MainActivity? = when (this) {
    is MainActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}