package com.ssafy.pickit.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imgUrl")
fun loadImage(view: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        view.setImageDrawable(null) // 기본 이미지 설정 없음
    } else {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}

