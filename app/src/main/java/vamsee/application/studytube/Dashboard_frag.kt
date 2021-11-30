package vamsee.application.studytube

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_dashboard_frag.*
import vamsee.application.studytube.Adapter.DashboardAdapter
import vamsee.application.studytube.Adapter.DashboardVideoClick
import vamsee.application.studytube.Models.Dashboard
import vamsee.application.studytube.Repository.Repository
import vamsee.application.studytube.databinding.FragmentDashboardFragBinding

class Dashboard_frag : Fragment(), DashboardVideoClick {

    private var _binding: FragmentDashboardFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: DashboardAdapter
    var videos: ArrayList<Dashboard> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDashboardFragBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        binding.dashdp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.go_to_profile_frag)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        Glide.with(this).load(currentUser?.photoUrl).circleCrop().into(binding.dashdp)
        mAdapter = DashboardAdapter(this)
        binding.dashboardRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.dashboardRecyclerView.adapter = mAdapter
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        getVideoID()
    }

    private fun getVideoID() {
        viewModel.search("Android Development")
        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            val videoIDs = it.body()?.items
            Log.d("VideoIDs", videoIDs.toString())

            for (video in it.body()?.items!!) {
                if (video.id.videoId != null) {
                    getVideoDetails(video.id.videoId)
                } else {
                    continue
                }
            }
        })
    }

    private fun getVideoDetails(videoId: String) {
        viewModel.getVideoDetails(videoId)
        SHOW_PROGRESS.visibility = View.VISIBLE
        Log.d("VIDEODETAILS", viewModel.videoResponse.value.toString())
        viewModel.videoResponse.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful) {

                //it.body()?.items!![0]?.snippet?.channelId?.let { it1 -> getChannelDetails(it1) }
//                it.body()?.items!![0]?.snippet?.channelId?.let { it1 ->
//                    viewModel.getChannelDetails(
//                        it1
//                    )
//                }
                videos.add(Dashboard(it.body()?.items?.get(0)!!,""))
                mAdapter.updateItems(videos)
                SHOW_PROGRESS.visibility = View.GONE
//

            } else {
                Toast.makeText(
                    context,
                    "Could not update the dashboard, please check your internet connection....",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("API", "you are ded!!!")
            }
        })

    }

    private fun getChannelDetails(id: String) {
       // viewModel.channelResponse.observe(viewLifecycleOwner, Observer {response ->
//                    if (response.isSuccessful){
//                        mAdapter.updateLogo(response.body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url.toString())
//                        //Log.d("DP", response.body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url.toString())
//
//                    }
//                    else{
//                        Log.d("API", "you are ded!!!")
//                    }
//                })

        //val dp = viewModel.channelResponse[0].body()?.items?.get(0)?.snippet?.thumbnails?.get("medium")?.url
        //Log.d("DP", dp.toString())
    }

    override fun onDashboardVideoClick(item: Dashboard) {
        val intent_video = Intent(context, videoPlayer::class.java)
        intent_video.putExtra("videoDetails", item.video)
        intent_video.putExtra("channelLogo", item.channelLogo)
        startActivity(intent_video)
    }

}
