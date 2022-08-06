package com.rui.base.extensions

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true