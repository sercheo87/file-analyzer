package com.sercheo.fileanalyzer.helpers

import java.text.NumberFormat
import java.util.*

fun formatNumber(number: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return formatter.format(number)
}
