package vamsee.application.studytube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.studytube.Adapter.PlaylistAdapter
import vamsee.application.studytube.Models.Search
import vamsee.application.studytube.Models.Video.VideoDetails
import vamsee.application.studytube.Models.Video.VideoResponse
import vamsee.application.studytube.Repository.Repository

class SkillDescription : AppCompatActivity() {

    lateinit var mAdapter: PlaylistAdapter
    private lateinit var viewModel: MainViewModel
   // var mPlaylist: ArrayList<VideoDetails> = ArrayList()
    var ids: ArrayList<String> = ArrayList()
    var IDS: List<Search> = listOf()
    var videos: ArrayList<VideoResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_description)

        val recyclerView: RecyclerView = findViewById(R.id.playlist)
        ids.add("vmseeee")
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        getVideoID()
        mAdapter = PlaylistAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
    }


    private fun getVideoID() {
        viewModel.search()
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
                mAdapter.setData(videos)
            }
            else{
                Log.d("API", "you are ded!!!")
            }
        })

    }
}