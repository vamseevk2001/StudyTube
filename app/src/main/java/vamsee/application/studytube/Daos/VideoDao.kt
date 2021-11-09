package vamsee.application.studytube.Daos

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import vamsee.application.studytube.Models.WatchlistVideoID
import vamsee.application.studytube.Wishlist_frag
import java.lang.Exception
import java.lang.StringBuilder

class VideoDao {
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth
    val currentUserId = auth.currentUser!!.uid
    val videoCollection = db.collection("users").document(currentUserId).collection("wishlist")

    fun addVideo(videoId: String){

        GlobalScope.launch(Dispatchers.IO) {
            val userDao = UserDao()
           // val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)
            val currentTime = System.currentTimeMillis()
            val videoIdClass = WatchlistVideoID(videoId, currentTime)
            videoCollection.document().set(videoIdClass)
        }
    }

}