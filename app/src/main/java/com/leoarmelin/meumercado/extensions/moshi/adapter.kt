package com.leoarmelin.meumercado.extensions.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

inline fun <reified T> Moshi.listAdapter(): JsonAdapter<List<T>> =
    this.adapter(Types.newParameterizedType(List::class.java, T::class.java))