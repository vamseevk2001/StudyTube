package vamsee.application.studytube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.studytube.Adapter.SkillsAdapter
import vamsee.application.studytube.Models.Skills

class Explore : AppCompatActivity() {

    lateinit var mAdapter: SkillsAdapter
    val mSkills: ArrayList<Skills> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)
        val recyclerView: RecyclerView = findViewById(R.id.skills_recyclerView)

        mSkills.add(Skills("java", R.drawable.java))
        mSkills.add(Skills("Android", R.drawable.android))
        mSkills.add(Skills("Web", R.drawable.web))
        mSkills.add(Skills("Blockchain", R.drawable.blockchain))
        mSkills.add(Skills("C++", R.drawable.cpp))
        mSkills.add(Skills("python", R.drawable.python))
        mSkills.add(Skills("C", R.drawable.c))
        mSkills.add(Skills("Javascript", R.drawable.javascript))

        mAdapter = SkillsAdapter()
        mAdapter.updateSkills(mSkills)
        recyclerView.adapter = mAdapter
        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = gridLayoutManager

    }
}