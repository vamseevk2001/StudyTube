package vamsee.application.studytube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.studytube.Adapter.PlaylistAdapter
import vamsee.application.studytube.Models.Playlist

class SkillDescription : AppCompatActivity() {

    lateinit var  mAdapter: PlaylistAdapter
    var mPlaylist: ArrayList<Playlist> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_description)

        val recyclerView: RecyclerView = findViewById(R.id.playlist)

        mPlaylist.add(Playlist("Android Development Tutorial for Beginners",
            R.drawable.tn1, "Anuj Bhaiya", 19))
        mPlaylist.add(Playlist("Android Development Tutorials in Hindi",
            R.drawable.tn2, "CodeWithHarry", 23))
        mPlaylist.add(Playlist("Android Developer Fundamentals",
            R.drawable.th3, "Google Developers India", 69))
        mPlaylist.add(Playlist("The Complete Android App Developer Course",
            R.drawable.th4, "Master Coding", 228))
        mPlaylist.add(Playlist(" Complete Android n Developer Course",
            R.drawable.tn5, "Free Udemy Course", 19))
        mPlaylist.add(Playlist("Android Developer Fundamentals",
            R.drawable.th3, "Google Developers India", 69))
        mPlaylist.add(Playlist("The Complete Android App Developer Course",
            R.drawable.th4, "Master Coding", 228))
        mPlaylist.add(Playlist(" Complete Android n Developer Course",
            R.drawable.tn5, "Free Udemy Course", 19))

        mAdapter = PlaylistAdapter()
        mAdapter.updateItems(mPlaylist)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter

    }
}