package vamsee.application.studytube.Adapter

import android.content.Context
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
        //Glide.with(Context).load(currentItem.thumbnails.standard.url).into(holder.thumbnail)
        Picasso.get().load(currentItem.snippet.thumbnails.get("high")?.url).into(holder.thumbnail)
        holder.creater.text = currentItem.snippet.channelTitle
        //holder.count.text = currentItem.count.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(array: ArrayList<VideoResponse>){
        items.clear()
        items.addAll(array.distinct())
        notifyDataSetChanged()
    }

}

class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val thumbnail: ImageView = itemView.findViewById(R.id.playlist_thumbnail)
    val title: TextView = itemView.findViewById(R.id.playlistTitle)
   // val count: TextView = itemView.findViewById(R.id.no_of_videos)
    val creater: TextView = itemView.findViewById(R.id.youtuberName)
}