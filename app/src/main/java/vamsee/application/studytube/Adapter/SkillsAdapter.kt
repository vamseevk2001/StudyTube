package vamsee.application.studytube.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.studytube.R
import vamsee.application.studytube.Skills

class SkillsAdapter: RecyclerView.Adapter<SkillViewHolder>() {

    private val items: ArrayList<Skills> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
        val viewHolder = SkillViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val currentItem = items[position]
        holder.skill_name.text = currentItem.name
        holder.skill_icon.setImageResource(currentItem.img)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateSkills(updated: ArrayList<Skills>){
        items.clear()
        items.addAll(updated)
        notifyDataSetChanged()
    }
}

class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val skill_name = itemView.findViewById<TextView>(R.id.skill_name)
    val skill_icon = itemView.findViewById<ImageView>(R.id.skill_icon)

}