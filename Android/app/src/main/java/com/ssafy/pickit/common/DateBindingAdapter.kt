package com.ssafy.pickit.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(): LocalDateTime? {
    return try {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
    } catch (e: Exception) {
        null
    }
}

@BindingAdapter("app:formattedDateTime")
fun setFormattedDateTime(textView: TextView, dateTimeString: String?) {
    dateTimeString?.let {
        val localDateTime = it.toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm")
        textView.text = localDateTime?.format(formatter) ?: ""
    } ?: run {
        textView.text = ""
    }
}