package vamsee.application.studytube.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vamsee.application.studytube.Models.Video.VideoResponse
import vamsee.application.studytube.R
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class DashboardAdapter: RecyclerView.Adapter<DashboardViewHolder>() {

    val itemList: ArrayList<VideoResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_item, parent, false)
        val viewHolder = DashboardViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val currentItem = itemList.get(position)
        Glide.with(holder.itemView.context).load(currentItem.snippet?.thumbnails?.standard?.url).into(holder.thumbnail)
        holder.videoTitle.text = currentItem.snippet?.title.toString()
        Glide.with(holder.itemView.context).load("https://i.imgur.com/JR8ilHf.jpg").circleCrop().into(holder.channelLogo)
        holder.videoInfo.text = currentItem.snippet?.channelTitle +  " . " + currentItem.statistics?.viewCount?.toInt()?.let {
            prettyCount(it)
        } + " views "
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateItems(array: ArrayList<VideoResponse>){
        itemList.clear()
        itemList.addAll(array.distinct())
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

class DashboardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail_dashboard)
    val channelLogo = itemView.findViewById<ImageView>(R.id.channel_logo)
    val videoTitle = itemView.findViewById<TextView>(R.id.video_title_dashboard)
    val videoInfo = itemView.findViewById<TextView>(R.id.video_info)
}