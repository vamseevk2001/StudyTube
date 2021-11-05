package vamsee.application.studytube

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.studytube.Adapter.SkillClick
import vamsee.application.studytube.Adapter.SkillsAdapter
import vamsee.application.studytube.Models.Skills
import vamsee.application.studytube.databinding.FragmentExploreFragBinding

class explore_frag : Fragment(), SkillClick {

    private var _binding: FragmentExploreFragBinding? = null
    private val binding get() = _binding!!

    lateinit var mAdapter: SkillsAdapter
    val mSkills: ArrayList<Skills> = ArrayList()

    private lateinit var skilsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExploreFragBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        skilsRecyclerView = binding.skillsRecyclerView

        mSkills.add(Skills("java", R.drawable.java))
        mSkills.add(Skills("Android", R.drawable.android))
        mSkills.add(Skills("Web", R.drawable.web))
        mSkills.add(Skills("Blockchain", R.drawable.blockchain))
        mSkills.add(Skills("C++", R.drawable.cpp))
        mSkills.add(Skills("python", R.drawable.python))
        mSkills.add(Skills("C", R.drawable.c))
        mSkills.add(Skills("Javascript", R.drawable.javascript))

        mAdapter = SkillsAdapter(this)
        mAdapter.updateSkills(mSkills)
        skilsRecyclerView.adapter = mAdapter
        val gridLayoutManager = GridLayoutManager(context, 2)
        skilsRecyclerView.layoutManager = gridLayoutManager

    }

    override fun onSkillClick(item: Skills) {
        val intent = Intent(activity, SkillDescription::class.java)
        if (item.name == "Android" || item.name == "Web"){
            intent.putExtra("skill", item.name + " Development")
        }
        else{
            intent.putExtra("skill", item.name)
        }

        startActivity(intent)
    }


}