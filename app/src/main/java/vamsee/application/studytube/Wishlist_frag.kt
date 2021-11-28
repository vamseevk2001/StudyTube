package vamsee.application.studytube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_wishlist_frag.*
import vamsee.application.studytube.Adapter.WishlistAdapter
import vamsee.application.studytube.Models.Video.VideoResponse
import vamsee.application.studytube.databinding.FragmentWishlistFragBinding

class Wishlist_frag : Fragment() {
    val auth = Firebase.auth
    val currentUserId = auth.currentUser!!.uid
    val videoCollection = FirebaseFirestore.getInstance().collection("users").document(currentUserId)
            .collection("wishlist")
    private var _binding: FragmentWishlistFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: WishlistAdapter

    private fun setUpRecyclerView() {
        val query = videoCollection.orderBy("createdBy", Query.Direction.ASCENDING)
        val recyclerViewOption = FirestoreRecyclerOptions.Builder<VideoResponse>()
            .setQuery(videoCollection, VideoResponse::class.java).build()
        adapter = context?.let { WishlistAdapter(it, recyclerViewOption) }!!
        wishhlist_recy.adapter = adapter
        wishhlist_recy.layoutManager = LinearLayoutManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistFragBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView()
    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}