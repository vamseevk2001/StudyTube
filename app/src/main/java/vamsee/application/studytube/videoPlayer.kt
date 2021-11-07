package vamsee.application.studytube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.internal.LifecycleActivity
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener
import vamsee.application.studytube.Models.VideoID
import vamsee.application.studytube.Repository.Repository

import vamsee.application.studytube.videoPlayer
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow


class videoPlayer : YouTubeBaseActivity() {

    private lateinit var youTubePlayerView: YouTubePlayerView
    var fullScreen: Boolean = false
    private lateinit var videoID: String
    private lateinit var title: TextView
    private lateinit var viewCount: TextView
    private lateinit var likeCount: TextView
    private lateinit var dislikeCount: TextView
    private lateinit var uploadTime: TextView
    private lateinit var videoDesc: TextView
    private lateinit var youtuberName: TextView
    private lateinit var subscriberCount: TextView
    private lateinit var dp: ImageView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        youTubePlayerView = findViewById(R.id.YoutubeVideoPlayer)

        val api_key = "AIzaSyAzSRqUWAASXDkNOeS4mkbWVo7QKHUhOo4"

        videoID = intent.getStringExtra("videoID").toString()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
//        viewModel = ViewModelProvider.of((AppCompatActivity) getActivity(), viewModelFactory).get(MainViewModel::class.java)
        viewModel =
            ViewModelProvider(applicationContext as AppCompatActivity, viewModelFactory).get(
                MainViewModel::class.java
            )

        title = findViewById(R.id.VideoTitle)
        viewCount = findViewById(R.id.VideoViews)
        likeCount = findViewById(R.id.VideoLikes)
        dislikeCount = findViewById(R.id.VideoDislikes)
        uploadTime = findViewById(R.id.UploadDate)
        videoDesc = findViewById(R.id.VideoDesc)
        youtuberName = findViewById(R.id.youtuberName)
        subscriberCount = findViewById(R.id.SubscriberCount)
        dp = findViewById(R.id.youtubeDP)


        youTubePlayerView.initialize(api_key, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer, wasRestored: Boolean
            ) {
                if (!wasRestored) {
                    player.setOnFullscreenListener(OnFullscreenListener { _isFullScreen ->
                        fullScreen = _isFullScreen
                    })
                    player.loadVideo(videoID)
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

        title.text = intent.getStringExtra("title")
        viewCount.text = intent.getStringExtra("views")?.toInt()?.let { prettyCount(it) } + " views"
        likeCount.text = intent.getStringExtra("likes")?.toInt()?.let { prettyCount(it) }
        dislikeCount.text = intent.getStringExtra("dislike")?.toInt()?.let { prettyCount(it) }
        videoDesc.text = intent.getStringExtra("desc")
        youtuberName.text = intent.getStringExtra("channelName")

        // getChannelDetails(intent.getStringExtra("channelID").toString())

//        title.text = intent.getStringExtra("title")
//        title.text = intent.getStringExtra("title")

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

//    private fun getChannelDetails(id: String){
//        viewModel.getChannelDetails(id)
//        viewModel.channelResponse.observe(applicationContext as (AppCompatActivity), Observer {
//            if (it.isSuccessful){
//                it.body()?.items?.get(0)?.statistics?.subscriberCount?.let { it1 ->
//                    Log.d("COUNT",
//                        it1
//                    )
//                }
//
//                subscriberCount.text = it.body()?.items?.get(0)?.statistics?.subscriberCount
//                Glide.with(this).load(it.body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url).circleCrop().into(dp)
//
////                intent_video.putExtra("count", it.body()?.items?.get(0)?.statistics?.subscriberCount)
////                intent_video.putExtra("dp", it.body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url.toString())
//                Log.d("DP", it.body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url.toString())
//
//            }
//            else{
//                Log.d("API", "you are ded!!!")
//            }
//        })

    //val dp = viewModel.channelResponse[0].body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url
    //Log.d("DP", dp.toString())
}


class get {

}