package com.studyhub.app

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView

class LessonAdapter(private val lessons: List<Lesson>) :
    RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    private var playingPosition: Int = -1
    private var activeMediaController: MediaController? = null

    class LessonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLessonTitle: TextView = view.findViewById(R.id.tvLessonTitle)
        val tvLessonLabel: TextView = view.findViewById(R.id.tvLessonLabel)
        val videoView: VideoView = view.findViewById(R.id.videoView)
        val ivThumbnail: ImageView = view.findViewById(R.id.ivThumbnail)
        val ivPlayButton: ImageView = view.findViewById(R.id.ivPlayButton)
        var currentVideoResId: Int = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lesson, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessons[position]
        val context = holder.itemView.context
        
        holder.tvLessonTitle.text = lesson.title
        holder.tvLessonLabel.text = context.getString(R.string.lesson_count, position + 1)
        holder.ivThumbnail.setImageResource(lesson.thumbnailResId)

        if (position == playingPosition) {
            // Show video UI components
            holder.videoView.visibility = View.VISIBLE
            holder.ivThumbnail.visibility = View.GONE
            holder.ivPlayButton.visibility = View.GONE
            holder.tvLessonLabel.visibility = View.GONE
            
            val resourceName = context.resources.getResourceEntryName(lesson.videoResId)
            val videoUri = Uri.parse("android.resource://${context.packageName}/raw/$resourceName")
            
            // Only setup the video if it's not already pointing to this resource
            if (holder.currentVideoResId != lesson.videoResId) {
                holder.videoView.stopPlayback() // Clear previous video state
                holder.videoView.setVideoURI(null) // Reset URI

                holder.currentVideoResId = lesson.videoResId
                holder.videoView.setVideoURI(videoUri)
                
                val mc = MediaController(context)
                mc.setAnchorView(holder.videoView)
                holder.videoView.setMediaController(mc)
                activeMediaController = mc

                holder.videoView.setOnPreparedListener { mp ->
                    mp.isLooping = false
                    holder.videoView.start()
                    // Show controls briefly on start
                    mc.show(2000)
                }

                holder.videoView.setOnCompletionListener {
                    if (playingPosition == holder.adapterPosition) {
                        stopActiveVideo()
                    }
                }

                holder.videoView.setOnErrorListener { _, _, _ ->
                    if (playingPosition == holder.adapterPosition) {
                        stopActiveVideo()
                    }
                    false
                }
            } else if (!holder.videoView.isPlaying) {
                // If already prepared but stopped/paused, resume playback
                holder.videoView.start()
            }
        } else {
            // Reset this holder's UI to the thumbnail state
            resetHolderUI(holder)
        }

        holder.ivPlayButton.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION && currentPos != playingPosition) {
                val oldPos = playingPosition
                playingPosition = currentPos
                
                // Use Handler to avoid issues with calling notifyItemChanged during layout
                Handler(Looper.getMainLooper()).post {
                    if (oldPos != -1) notifyItemChanged(oldPos)
                    notifyItemChanged(playingPosition)
                }
            }
        }
    }

    private fun resetHolderUI(holder: LessonViewHolder) {
        // Stop playback and release the controller for this holder
        holder.videoView.stopPlayback()
        holder.videoView.setMediaController(null)
        holder.currentVideoResId = 0
        
        holder.videoView.visibility = View.GONE
        holder.ivThumbnail.visibility = View.VISIBLE
        holder.ivPlayButton.visibility = View.VISIBLE
        holder.tvLessonLabel.visibility = View.VISIBLE
    }

    override fun onViewRecycled(holder: LessonViewHolder) {
        super.onViewRecycled(holder)
        // Clean up resources on recycle to stop audio and free memory
        resetHolderUI(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                // Hide floating MediaController while scrolling to prevent UI artifacting
                activeMediaController?.hide()
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // Ensure floating MediaController stays hidden during the movement
                activeMediaController?.hide()
            }
        })
    }

    private fun stopActiveVideo() {
        activeMediaController?.hide()
        activeMediaController = null
        
        if (playingPosition != -1) {
            val pos = playingPosition
            playingPosition = -1
            Handler(Looper.getMainLooper()).post {
                notifyItemChanged(pos)
            }
        }
    }

    override fun getItemCount() = lessons.size
}
