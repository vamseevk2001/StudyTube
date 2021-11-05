package vamsee.application.studytube.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import vamsee.application.studytube.Models.Video.VideoDetails
import vamsee.application.studytube.Models.Video.VideoResponse
import vamsee.application.studytube.R
import vamsee.application.studytube.SkillDescription
import java.lang.String
import java.text.DecimalFormat
import java.time.Duration
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class PlaylistAdapter: RecyclerView.Adapter<PlaylistViewHolder>() {

    private val items: ArrayList<VideoResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        val viewHolder = PlaylistViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val currentItem = items[position]
        holder.title.text = currentItem.snippet.title
        Picasso.get().load(currentItem.snippet.thumbnails["standard"]?.url).placeholder(R.drawable.th3).into(holder.thumbnail)
        holder.creater.text = currentItem.snippet.channelTitle
        var result = currentItem.contentDetails.duration
        var string = result.drop(2)
        Log.d("DURATION", string)

        string = string.replace('H', ':')
        string = string.replace('M', ':')
        string = string.dropLast(1)

        holder.duration.text = string

        holder.likes.text = " " + prettyCount(currentItem.statistics.likeCount.toInt())
        holder.dislikes.text = " " + prettyCount(currentItem.statistics.dislikeCount.toInt())
        holder.views.text = prettyCount(currentItem.statistics.viewCount.toInt()) + " views"

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(array: ArrayList<VideoResponse>){
        items.clear()
        items.addAll(array.distinct())
        notifyDataSetChanged()
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

class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val thumbnail: ImageView = itemView.findViewById(R.id.playlist_thumbnail)
    val title: TextView = itemView.findViewById(R.id.playlistTitle)
   // val count: TextView = itemView.findViewById(R.id.no_of_videos)
    val duration: TextView = itemView.findViewById(R.id.duration)
    val views: TextView = itemView.findViewById(R.id.views)
    val likes: TextView = itemView.findViewById(R.id.likes)
    val dislikes: TextView = itemView.findViewById(R.id.dislikes)
    val creater: TextView = itemView.findViewById(R.id.youtuberName)
}