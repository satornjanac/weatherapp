package com.satornjanac.weatherforecastapp.extensions

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale

fun String.toDay(): String {
    Log.e("***ARM***", this)
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    val date = simpleDateFormat.parse(this)
    val finalDataFormat = SimpleDateFormat("EEE", Locale.getDefault())
    return finalDataFormat.format(date)
}

fun String.toDayShorterFormat(): String {
    Log.e("***ARM***", this)
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = simpleDateFormat.parse(this)
    val finalDataFormat = SimpleDateFormat("EEE", Locale.getDefault())
    return finalDataFormat.format(date)
}

fun String.toHour(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    val date = simpleDateFormat.parse(this)
    val finalDataFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return finalDataFormat.format(date)
}