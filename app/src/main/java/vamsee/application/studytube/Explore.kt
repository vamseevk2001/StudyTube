package vamsee.application.studytube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.studytube.Adapter.SkillsAdapter

class Explore : AppCompatActivity() {

    lateinit var mAdapter: SkillsAdapter
    val mSkills: ArrayList<Skills> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)
        val recyclerView: RecyclerView = findViewById(R.id.skills_recyclerView)

        mSkills.add(Skills("java", ""))
        mSkills.add(Skills("Android", ""))
        mSkills.add(Skills("Web", ""))
        mSkills.add(Skills("Blockchain", ""))
        mSkills.add(Skills("C++", ""))
        mSkills.add(Skills("python", ""))
        mSkills.add(Skills("C", ""))
        mSkills.add(Skills("Javascript", ""))

        mAdapter = SkillsAdapter()
        mAdapter.updateSkills(mSkills)
        recyclerView.adapter = mAdapter
        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = gridLayoutManager

    }
}