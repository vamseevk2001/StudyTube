package vamsee.application.studytube

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_video_player.*
import vamsee.application.studytube.Daos.VideoDao
import vamsee.application.studytube.Models.Video.VideoResponse
import vamsee.application.studytube.videoPlayer
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow


class videoPlayer : YouTubeBaseActivity() {

    private lateinit var youTubePlayerView: YouTubePlayerView
    var fullScreen: Boolean = false
    private lateinit var videoID: String
    private lateinit var videoDetails: VideoResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        youTubePlayerView = findViewById(R.id.YoutubeVideoPlayer)

        val api_key = "AIzaSyAzSRqUWAASXDkNOeS4mkbWVo7QKHUhOo4"

        videoID = intent.getStringExtra("videoID").toString()

        if (intent.hasExtra("videoDetails")){
            videoDetails = intent.getParcelableExtra<VideoResponse>("videoDetails")!!
            Log.d("VIDEO_PLAYER", videoDetails.toString())
        }

        watchlistImage.setOnClickListener {
            watchlist()
        }

        youTubePlayerView.initialize(api_key, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer, wasRestored: Boolean
            ) {
                if (!wasRestored) {
                    player.setOnFullscreenListener(OnFullscreenListener { _isFullScreen ->
                        fullScreen = _isFullScreen
                    })
                    player.loadVideo(videoDetails.id)
                }
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@videoPlayer, "Video player Failed", Toast.LENGTH_SHORT).show()
            }
        })

        setDetails()
    }

    fun setDetails() {

        VideoTitle.text = videoDetails.snippet?.title
        VideoViews.text = videoDetails.statistics?.viewCount?.toInt()?.let { prettyCount(it) } + " views"
        VideoLikes.text = videoDetails.statistics?.likeCount?.toInt()?.let { prettyCount(it) }
        VideoDislikes.text = videoDetails.statistics?.dislikeCount?.toInt()?.let { prettyCount(it) }
        VideoDesc.text = videoDetails.snippet?.description
        youtuberName.text = videoDetails.snippet?.channelTitle
        if (intent.getStringExtra("count").isNullOrBlank()){
            SubscriberCount.text = prettyCount(123133)
        }
        else{
            SubscriberCount.text = intent.getStringExtra("count")?.toInt()?.let { prettyCount(it) } + " Subscribers"
            Glide.with(this).load(intent.getStringExtra("dp").toString()).placeholder(R.drawable.kotlin).circleCrop().into(youtubeDP)
        }
        // getChannelDetails(intent.getStringExtra("channelID").toString())

    }

    fun prettyCount(number: Number): kotlin.String? {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val numValue = number.toLong()
        val value = floor(log10(numValue.toDouble())).toInt()
        val base = value / 3
        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(
                numValue / 10.0.pow((base * 3).toDouble())
            ) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(numValue)
        }
    }

    private fun watchlist(){
        val videoDao = VideoDao()
        videoDao.addVideo(videoDetails)
        Toast.makeText(this, "Video added to watchlist successfully", Toast.LENGTH_SHORT).show()
    }

}


class get {

}