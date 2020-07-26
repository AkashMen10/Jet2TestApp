package com.test.jet2app.utils

import android.content.Context
import com.test.jet2app.R
import java.text.DecimalFormat
import kotlin.math.abs


fun Int.getNumberInDisplayFormat(context: Context, suffix: String): String {
    return if (abs(this / 1000) > 1)
        "${DecimalFormat("##.##").format((toDouble() / 1000))}${context.getString(R.string.k_suffix)} $suffix"
    else
        "$this $suffix"
}
