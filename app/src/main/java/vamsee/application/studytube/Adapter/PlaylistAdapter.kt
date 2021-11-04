package vamsee.application.studytube.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.studytube.Models.Video.VideoDetails
import vamsee.application.studytube.R

class PlaylistAdapter: RecyclerView.Adapter<PlaylistViewHolder>() {

    private val items: ArrayList<VideoDetails> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        val viewHolder = PlaylistViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val currentItem = items[position]
        //holder.thumbnail.setImageResource(currentItem.thumbnail)
        holder.title.text = currentItem.title
        holder.creater.text = currentItem.channelTitle
        //holder.count.text = currentItem.count.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(array: ArrayList<VideoDetails>){
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