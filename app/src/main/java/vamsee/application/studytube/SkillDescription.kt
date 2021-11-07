package vamsee.application.studytube

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.studytube.Adapter.PlaylistAdapter
import vamsee.application.studytube.Adapter.videoClick
import vamsee.application.studytube.Models.Search
import vamsee.application.studytube.Models.Video.VideoResponse
import vamsee.application.studytube.Repository.Repository

class SkillDescription : AppCompatActivity(), videoClick {

    lateinit var mAdapter: PlaylistAdapter
    private lateinit var viewModel: MainViewModel
    var ids: ArrayList<String> = ArrayList()
    var IDS: List<Search> = listOf()
    var videos: ArrayList<VideoResponse> = arrayListOf()
    private lateinit var intent_video: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_description)

        intent_video = Intent(this, videoPlayer::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.playlist)
        ids.add("vmseeee")
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        getVideoID()
        mAdapter = PlaylistAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter

    }


    private fun getVideoID() {
        val name = intent.getStringExtra("skill").toString()
        val desc_title: TextView = findViewById(R.id.skill_name_desc)
        desc_title.text = name
        viewModel.search(name)
        viewModel.myResponse.observe(this, Observer {
            Log.d("Video", it.body()?.items.toString())
            //Log.d("SIZE", it.body()?.items?.size!!.toString())
//            IDS = it.body()?.items!!

            val videoIDs = it.body()?.items
            Log.d("VideoIDs", videoIDs.toString())
            for (video in it.body()?.items!!){
                if (video.id.videoId != null){
                    Log.d("VIDEO", video.id.videoId)
                    getVideoDetails(video.id.videoId)
                }
                else{
                    continue
                }
            }
        })

        Log.d("VIDEO", viewModel.id.toString())

        Log.d("VideoID", IDS.toString())
    }

    private fun getVideoDetails(videoId: String){
        viewModel.getVideoDetails(videoId)
        viewModel.videoResponse.observe(this, Observer {
            if (it.isSuccessful){
                videos.add(it.body()?.items?.get(0)!!)
                //getChannelDetails(it.body()!!.items[0].snippet.channelId)
                mAdapter.setData(videos)
            }
            else{
                Log.d("API", "you are ded!!!")
            }
        })

    }

    private fun getChannelDetails(id: String){
        viewModel.getChannelDetails(id)
        viewModel.channelResponse.observe(this, Observer {
            if (it.isSuccessful){
                it.body()?.items?.get(0)?.statistics?.subscriberCount?.let { it1 ->
                    Log.d("COUNT",
                        it1
                    )
                }
                intent_video.putExtra("count", it.body()?.items?.get(0)?.statistics?.subscriberCount)
                intent_video.putExtra("dp", it.body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url.toString())
                Log.d("DP", it.body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url.toString())

            }
            else{
                Log.d("API", "you are ded!!!")
            }
        })

        //val dp = viewModel.channelResponse[0].body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url
        //Log.d("DP", dp.toString())
    }

    fun goBackSkills(view: View) {
        val intent = Intent(this, explore_frag::class.java)
        startActivity(intent)
    }


    override fun onVideoClick(item: VideoResponse) {
        getChannelDetails(item.snippet.channelId)

        intent_video.putExtra("videoID", item.id)
        intent_video.putExtra("title", item.snippet.title)
        intent_video.putExtra("channelName", item.snippet.channelTitle)
        intent_video.putExtra("channelID", item.snippet.channelId)
        intent_video.putExtra("likes", item.statistics.likeCount)
        intent_video.putExtra("dislike", item.statistics.dislikeCount)
        intent_video.putExtra("views", item.statistics.viewCount)
        intent_video.putExtra("desc", item.snippet.description)
        intent_video.putExtra("time", item.snippet.publishedAt)
        startActivity(intent_video)
    }
}

data class channel_details(
    val subs: String,
    val dp: String
)