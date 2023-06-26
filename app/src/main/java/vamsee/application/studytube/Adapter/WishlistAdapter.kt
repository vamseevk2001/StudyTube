package vamsee.application.studytube.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import vamsee.application.studytube.Models.Video.VideoResponse
import vamsee.application.studytube.R
import vamsee.application.studytube.videoPlayer
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow


class WishlistAdapter (private val context: Context, options: FirestoreRecyclerOptions<VideoResponse>) : FirestoreRecyclerAdapter<VideoResponse, WishlistAdapter.RequestViewHolder>(
        options) {

        class RequestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val thumbnail: ImageView = itemView.findViewById(R.id.playlist_thumbnail)
            val title: TextView = itemView.findViewById(R.id.playlistTitle)
            val loading: LottieAnimationView = itemView.findViewById(R.id.SHOW_PROGRESS)
            val duration: TextView = itemView.findViewById(R.id.duration)
            val views: TextView = itemView.findViewById(R.id.views)
            val likes: TextView = itemView.findViewById(R.id.likes)
            val creator: TextView = itemView.findViewById(R.id.youtuberName)
            val view: View = itemView
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false);
            val viewHolder =  RequestViewHolder(view)
            return viewHolder
        }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int, model: VideoResponse) {
        holder.title.text = model.snippet?.title
        holder.loading.visibility = View.VISIBLE
        Glide.with(holder.itemView.context).load(model.snippet?.thumbnails?.standard?.url).listener(object :
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.loading.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.loading.visibility = View.GONE
                return false
            }

        }).into(holder.thumbnail)
        holder.creator.text = model.snippet?.channelTitle
        var result = model.contentDetails?.duration
        var string = result?.drop(2)
        if (string != null) {
            Log.d("DURATION", string)
        }

        if (string != null) {
            string = string.replace('H', ':')
        }
        string = string?.replace('M', ':')
        string = string?.dropLast(1)

        holder.duration.text = string

        holder.likes.text = " " + model.statistics?.likeCount?.let { prettyCount(it.toInt()) }
//        holder.dislikes.text = " " + model.statistics?.dislikeCount?.toInt()?.let {
//            prettyCount(
//                it
//            )
//        }
        holder.views.text = model.statistics?.viewCount?.toInt()?.let { prettyCount(it) } + " views"

        holder.view.setOnClickListener{
            val intent_video = Intent(context, videoPlayer::class.java)
            intent_video.putExtra("videoDetails", model)
            context.startActivity(intent_video)
        }
    }

    fun prettyCount(number: Number): kotlin.String? {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val numValue = number.toLong()
        val value = floor(log10(numValue.toDouble())).toInt()
        val base = value / 3
        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(
                numValue / 10.0.pow((base * 3).toDouble())
            ) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(numValue)
        }
    }


}
