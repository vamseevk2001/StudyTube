package vamsee.application.studytube.Daos

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

data class WatchlistVideoID(
    val videoId: String,
    val currentTimestamp: Long
)