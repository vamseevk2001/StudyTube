package vamsee.application.studytube.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vamsee.application.studytube.Models.Dashboard
import vamsee.application.studytube.R
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class DashboardAdapter(private val listner: DashboardVideoClick): RecyclerView.Adapter<DashboardViewHolder>() {

    val itemList: ArrayList<Dashboard> = ArrayList()
    val logos: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_item, parent, false)
        val viewHolder = DashboardViewHolder(view)
        view.setOnClickListener{
            listner.onDashboardVideoClick(itemList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val currentItem = itemList.get(position)
        Glide.with(holder.itemView.context).load(currentItem.video?.snippet?.thumbnails?.standard?.url).into(holder.thumbnail)
        holder.videoTitle.text = currentItem.video?.snippet?.title.toString()
//        if (logos.isEmpty() || position >= logos.size){
//            Glide.with(holder.itemView.context).load("https://i.imgur.com/JR8ilHf.jpg").circleCrop().into(holder.channelLogo)
//        }
//        else
        Glide.with(holder.itemView.context).load(currentItem.channelLogo).circleCrop().into(holder.channelLogo)
        Glide.with(holder.itemView.context).load("https://i.imgur.com/JR8ilHf.jpg").circleCrop().into(holder.channelLogo)
        holder.videoInfo.text = currentItem.video?.snippet?.channelTitle +  " . " + currentItem.video?.statistics?.viewCount?.toInt()?.let {
            prettyCount(it)
        } + " views "
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateItems(array: ArrayList<Dashboard>){
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

    fun updateLogo(imgUrl: String) {
        logos.add(imgUrl)
    }

}

class DashboardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail_dashboard)
    val channelLogo = itemView.findViewById<ImageView>(R.id.channel_logo)
    val videoTitle = itemView.findViewById<TextView>(R.id.video_title_dashboard)
    val videoInfo = itemView.findViewById<TextView>(R.id.video_info)
}

interface DashboardVideoClick{
    fun onDashboardVideoClick(item: Dashboard)
}