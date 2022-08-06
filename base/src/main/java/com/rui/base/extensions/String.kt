package com.rui.base.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned

fun String.fromHtmlText(): Spanned =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }

fun String.contains(arrayList: ArrayList<String>, ignoreCase: Boolean) =
    arrayList.any { this.contains(it, ignoreCase) }