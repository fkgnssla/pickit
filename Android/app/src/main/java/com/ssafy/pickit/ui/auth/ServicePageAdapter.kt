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
        R.raw.video1,
        R.raw.video2,
        R.raw.video3
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


        holder.videoView.setVideoURI(videoUri)


        holder.videoView.setOnPreparedListener { mp ->
            mp.isLooping = true

            mp.start()
        }


        holder.videoView.setOnErrorListener { _, what, extra ->
            Log.e("ServicePageAdapter", "Error loading video: $videoUri, error code: $what, extra: $extra")
            true
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoView: VideoView = view.findViewById(R.id.videoView)
    }
}