package vamsee.application.studytube

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import vamsee.application.studytube.databinding.FragmentDashboardFragBinding
import vamsee.application.studytube.databinding.FragmentWishlistFragBinding
import java.lang.Exception
import java.lang.StringBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [Wishlist_frag.newInstance] factory method to
 * create an instance of this fragment.
 */
class Wishlist_frag : Fragment() {
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth
    val currentUserId = auth.currentUser!!.uid
    val videoCollection = db.collection("users").document(currentUserId).collection("wishlist")
    private var _binding: FragmentWishlistFragBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    retrieveVids()
    }
    private fun retrieveVids()= CoroutineScope(Dispatchers.IO).launch {
        try{
            val querySnapshot=videoCollection.get().await()
            val sb = StringBuilder()
            for(document in querySnapshot.documents){
                val vids=document.toObject<Wishlist_frag>()
                sb.append("$vids\n")
            }
            withContext(Dispatchers.Main){
            binding.wishtxt.text=sb.toString()
            }
        }catch (e: Exception){

        }
    }
}