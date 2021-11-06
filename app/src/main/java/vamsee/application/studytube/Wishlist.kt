package vamsee.application.studytube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.studytube.Adapter.PlaylistAdapter
import vamsee.application.studytube.Models.Video.VideoDetails

class Wishlist : AppCompatActivity() {

    //lateinit var  mAdapter: PlaylistAdapter
    var mPlaylist: ArrayList<VideoDetails> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val recyclerView: RecyclerView = findViewById(R.id.watchlist)

//        mPlaylist.add(Playlist("Android Development Tutorial for Beginners",
//            R.drawable.tn1, "Anuj Bhaiya", 19))
//        mPlaylist.add(Playlist("Android Development Tutorials in Hindi",
//            R.drawable.tn2, "CodeWithHarry", 23))
//        mPlaylist.add(Playlist("Android Developer Fundamentals",
//            R.drawable.th3, "Google Developers India", 69))


       // mAdapter = PlaylistAdapter()
       // mAdapter.updateItems(mPlaylist)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = mAdapter


    }
}