package vamsee.application.studytube.Daos

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vamsee.application.studytube.Models.Video.VideoResponse

class VideoDao {
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth
    val currentUserId = auth.currentUser!!.uid
    val videoCollection = db.collection("users").document(currentUserId).collection("wishlist")

    fun addVideo(video: VideoResponse) {

        GlobalScope.launch(Dispatchers.IO) {
            val userDao = UserDao()
            // val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)
            val currentTime = System.currentTimeMillis()
            //val videoIdClass = WatchlistVideoID(videoId, currentTime)
            //videoCollection.document().set(videoIdClass)
            videoCollection.document(video?.id.toString()).set(video)
        }
    }

    fun deleteVideo(context: Context, id: String) {
        videoCollection.document(id).delete().addOnSuccessListener {
            Toast.makeText(
                context,
                "Video deleted successfully from watchlist....",
                Toast.LENGTH_LONG
            ).show()
        }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "unable to delete video from watchlist....",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    fun check(id: String){
        videoCollection.document(id).get().addOnCompleteListener{
            if (it.isSuccessful){

            }
        }
    }



}