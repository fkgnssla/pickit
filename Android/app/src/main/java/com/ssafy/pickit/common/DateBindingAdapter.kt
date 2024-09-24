
package com.ssafy.pickit.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("formattedDateTime")
fun setFormattedDateTime(textView: TextView, dateTime: LocalDateTime?) {
    dateTime?.let {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm") // 원하는 포맷으로 변경
        textView.text = it.format(formatter)
    } ?: run {
        textView.text = ""
    }
}
