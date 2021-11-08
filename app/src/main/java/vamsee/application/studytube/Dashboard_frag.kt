package vamsee.application.studytube

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.youtube.player.internal.v
import com.google.firebase.auth.FirebaseAuth
import vamsee.application.studytube.databinding.FragmentDashboardFragBinding

class Dashboard_frag : Fragment() {

    private var _binding: FragmentDashboardFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    //private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDashboardFragBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        binding.dashdp.setOnClickListener { Navigation.findNavController(view).navigate(R.id.go_to_profile_frag) }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAuth= FirebaseAuth.getInstance()
        val currentUser=mAuth.currentUser
        binding.textView.text = "I am Here :)"
        Glide.with(this).load(currentUser?.photoUrl).circleCrop().into(binding.dashdp);
        }
    }
