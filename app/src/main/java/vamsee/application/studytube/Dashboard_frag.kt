package vamsee.application.studytube

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
import vamsee.application.studytube.Adapter.DashboardAdapter
import vamsee.application.studytube.Models.Video.VideoResponse
import vamsee.application.studytube.Repository.Repository
import vamsee.application.studytube.databinding.FragmentDashboardFragBinding
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class Dashboard_frag : Fragment() {

    private var _binding: FragmentDashboardFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: DashboardAdapter
    var videos: ArrayList<VideoResponse> = ArrayList()


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
        mAdapter = DashboardAdapter()
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
        //SHOW_PROGRESS.visibility = View.VISIBLE
        viewModel.videoResponse.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful) {
                videos.add(it.body()?.items?.get(0)!!)
                //mAdapter.setData(videos)
                mAdapter.updateItems(videos)
                //SHOW_PROGRESS.visibility = View.GONE
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


}
