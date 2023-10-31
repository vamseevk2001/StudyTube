package vamsee.application.studytube

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeBaseActivity
import com.jaedongchicken.ytplayer.YoutubePlayerView
import com.jaedongchicken.ytplayer.YoutubePlayerView.STATE
import com.jaedongchicken.ytplayer.YoutubePlayerView.YouTubeListener
import kotlinx.android.synthetic.main.activity_video_player.SubscriberCount
import kotlinx.android.synthetic.main.activity_video_player.VideoDesc
import kotlinx.android.synthetic.main.activity_video_player.VideoLikes
import kotlinx.android.synthetic.main.activity_video_player.VideoTitle
import kotlinx.android.synthetic.main.activity_video_player.VideoViews
import kotlinx.android.synthetic.main.activity_video_player.addToWatchList
import kotlinx.android.synthetic.main.activity_video_player.deletefromwatchlist
import kotlinx.android.synthetic.main.activity_video_player.deletefromwatchlistImgae
import kotlinx.android.synthetic.main.activity_video_player.watchlistImage
import kotlinx.android.synthetic.main.activity_video_player.youtubeDP
import kotlinx.android.synthetic.main.activity_video_player.youtuberName
import vamsee.application.studytube.Daos.VideoDao
import vamsee.application.studytube.Models.Video.VideoResponse
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow


class videoPlayer : YouTubeBaseActivity() {

    private lateinit var youTubePlayerView: YoutubePlayerView
    var fullScreen: Boolean = false
    private lateinit var videoDetails: VideoResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        youTubePlayerView = findViewById(R.id.YoutubeVideoPlayer)  as YoutubePlayerView

        if (intent.hasExtra("videoDetails")) {
            videoDetails = intent.getParcelableExtra<VideoResponse>("videoDetails")!!
            Log.d("VIDEO_PLAYER", videoDetails.toString())
        }

        watchlistImage.setOnClickListener {
            watchlist()
        }

        youTubePlayerView.setAutoPlayerHeight(this);

        youTubePlayerView.playFullscreen()
        youTubePlayerView.initialize( videoDetails.id, object : YouTubeListener{
            override fun onReady() {

            }

            override fun onStateChange(state: STATE?) {
                TODO("Not yet implemented")
            }

            override fun onPlaybackQualityChange(arg: String?) {
                TODO("Not yet implemented")
            }

            override fun onPlaybackRateChange(arg: String?) {
                TODO("Not yet implemented")
            }

            override fun onError(arg: String?) {
                TODO("Not yet implemented")
            }

            override fun onApiChange(arg: String?) {
                TODO("Not yet implemented")
            }

            override fun onCurrentSecond(second: Double) {
                TODO("Not yet implemented")
            }

            override fun onDuration(duration: Double) {
                TODO("Not yet implemented")
            }

            override fun logs(log: String?) {
                TODO("Not yet implemented")
            }

        })


//        youTubePlayerView.initialize(api_key, object : YouTubePlayer.OnInitializedListener {
//            override fun onInitializationSuccess(
//                provider: YouTubePlayer.Provider?,
//                player: YouTubePlayer, wasRestored: Boolean
//            ) {
//
//                if (!wasRestored) {
////                    player.setOnFullscreenListener(OnFullscreenListener { _isFullScreen ->
////                        fullScreen = _isFullScreen
////                    })
//                    player.loadVideo(videoDetails.id)
//                }
//            }
//
//            override fun onInitializationFailure(
//                p0: YouTubePlayer.Provider?,
//                p1: YouTubeInitializationResult?
//            ) {
//                Toast.makeText(this@videoPlayer, "Video player Failed", Toast.LENGTH_SHORT).show()
//            }
//        })

        setDetails()

        val videoDao = VideoDao()
        videoDetails.id?.let { it ->
            videoDao.videoCollection.document(it).get().addOnCompleteListener {
                if (it.result?.exists() == true){
                    watchlistImage.visibility = View.GONE
                    addToWatchList.visibility = View.GONE
                    deletefromwatchlistImgae.visibility = View.VISIBLE
                    deletefromwatchlist.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun setDetails() {
        VideoTitle.text = videoDetails.snippet?.title
        VideoViews.text = videoDetails.statistics?.viewCount?.toInt()?.let { prettyCount(it) } + " views"
        VideoLikes.text = videoDetails.statistics?.likeCount?.toInt()?.let { prettyCount(it) }
//        VideoDislikes.text = videoDetails.statistics?.dislikeCount?.toInt()?.let { prettyCount(it) }
        VideoDesc.text = videoDetails.snippet?.description
        youtuberName.text = videoDetails.snippet?.channelTitle
        if (intent.getStringExtra("count").isNullOrBlank()){
            Glide.with(this).load("https://i.imgur.com/JR8ilHf.jpg").placeholder(R.drawable.kotlin).circleCrop().into(youtubeDP)
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
        watchlistImage.visibility = View.GONE
        addToWatchList.visibility = View.GONE
        deletefromwatchlistImgae.visibility = View.VISIBLE
        deletefromwatchlist.visibility = View.VISIBLE
    }

    fun deleteVideo(view: View) {
        val videoDao = VideoDao()
        videoDetails.id?.let { videoDao.deleteVideo(this, it) }
        watchlistImage.visibility = View.VISIBLE
        addToWatchList.visibility = View.VISIBLE
        deletefromwatchlistImgae.visibility = View.GONE
        deletefromwatchlist.visibility = View.GONE
    }

}
