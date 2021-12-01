package vamsee.application.studytube

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import vamsee.application.studytube.databinding.FragmentUserVideosBinding

class userVideos_frag() : Fragment() {

    private var _binding: FragmentUserVideosBinding? = null
    private val binding get() = _binding!!
    val storage = Firebase.storage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserVideosBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.addvid.setOnClickListener {
            val intent = Intent(getActivity(), Uploadvid::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var StorageRef=FirebaseStorage.getInstance().getReference("Video")
        var DatabaseRef=FirebaseDatabase.getInstance().getReference("video")
        }
    }