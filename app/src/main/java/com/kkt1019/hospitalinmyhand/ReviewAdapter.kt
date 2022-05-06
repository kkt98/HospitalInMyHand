package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import org.w3c.dom.Text


class ReviewAdapter(val context: Context, var items: MutableList<ItemVO>) : RecyclerView.Adapter<ReviewAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivProfile : ImageView by lazy { itemView.findViewById(R.id.profile) }
        val tvId : TextView by lazy { itemView.findViewById(R.id.id) }
        val ivIv : ImageView by lazy { itemView.findViewById(R.id.iv) }
        val tvMessage : TextView by lazy { itemView.findViewById(kr.co.prnd.readmore.R.id.message) }
        val tvDate : TextView by lazy { itemView.findViewById(R.id.date) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater:LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_review_item, parent, false)

        return  VH(itemView)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item =items.get(position)

        Glide.with(context).load(item.profile).into(holder.ivProfile)

        val pictureUrl = "http://sux1011.dothome.co.kr/hospital/" + item.picture
        Glide.with(context).load(pictureUrl).into(holder.ivIv)

        holder.tvId.text = item.useremail
        holder.tvMessage.text = item.message
        holder.tvDate.text = item.date


    }

    override fun getItemCount(): Int = items.size
}