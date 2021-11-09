package vamsee.application.studytube.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import vamsee.application.studytube.R
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import vamsee.application.studytube.Models.WatchlistVideoID


class WishlistAdapter (options: FirestoreRecyclerOptions<WatchlistVideoID>) : FirestoreRecyclerAdapter<WatchlistVideoID, WishlistAdapter.RequestViewHolder>(
        options) {

        class RequestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val thumbnail: ImageView = itemView.findViewById(R.id.playlist_thumbnail)
            val title: TextView = itemView.findViewById(R.id.playlistTitle)
            val loading: LottieAnimationView = itemView.findViewById(R.id.SHOW_PROGRESS)
            val duration: TextView = itemView.findViewById(R.id.duration)
            val views: TextView = itemView.findViewById(R.id.views)
            val likes: TextView = itemView.findViewById(R.id.likes)
            val dislikes: TextView = itemView.findViewById(R.id.dislikes)
            val creator: TextView = itemView.findViewById(R.id.youtuberName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
            val viewHolder =  RequestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false))
            return viewHolder
        }

        override fun onBindViewHolder(holder: RequestViewHolder, position: Int, model: WatchlistVideoID) {
            holder.title.text=model.videoId

        }
    }