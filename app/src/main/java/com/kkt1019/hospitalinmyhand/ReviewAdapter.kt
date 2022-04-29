package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ReviewAdapter constructor(val context: Context, var items:MutableList<ReviewItem>) : RecyclerView.Adapter<ReviewAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivProfile : ImageView by lazy { itemView.findViewById(R.id.profile) }
        val ivId : TextView by lazy { itemView.findViewById(R.id.id) }
        val ivIv : ImageView by lazy { itemView.findViewById(R.id.iv) }
        val tvMessage : TextView by lazy { itemView.findViewById(R.id.message) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater:LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_review_item, parent, false)

        return  VH(itemView)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item =items.get(position)

        holder.ivProfile.setImageResource(item.profile)
        holder.ivId.text = item.nickname
        holder.ivIv.setImageResource(item.image)
        holder.tvMessage.text = item.message


    }

    override fun getItemCount(): Int = items.size
}