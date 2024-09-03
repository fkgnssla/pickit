package com.ssafy.pickit.ui.auth

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.pickit.R

class ServicePageAdapter(private val context: Context) : RecyclerView.Adapter<ServicePageAdapter.ViewHolder>() {

    private val videos = arrayOf(
        R.raw.video1,  // res/raw/video1.mp4
        R.raw.video2,  // res/raw/video2.mp4
        R.raw.video3   // res/raw/video3.mp4
    )

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.page_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actualPosition = position % videos.size
        val videoResId = videos[actualPosition]
        val videoUri = Uri.parse("android.resource://${context.packageName}/$videoResId")

        // 비디오 초기화
        holder.videoView.setVideoURI(videoUri)

        // 비디오 준비 리스너
        holder.videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            // 비디오 준비가 완료된 후 시작
            mp.start()
        }

        // 비디오 에러 리스너
        holder.videoView.setOnErrorListener { _, what, extra ->
            Log.e("ServicePageAdapter", "Error loading video: $videoUri, error code: $what, extra: $extra")
            true
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoView: VideoView = view.findViewById(R.id.videoView)
    }
}