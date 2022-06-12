package com.kkt1019.hospitalinmyhand

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.text.HtmlCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kkt1019.hospitalinmyhand.databinding.RecyclerMedicalItemBinding

class MedicalAdapter(val context:Context, var items: MutableList<MedicalItems>) : RecyclerView.Adapter<MedicalAdapter.VH>() {

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){

        val binding:RecyclerMedicalItemBinding = RecyclerMedicalItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_medical_item, parent, false)

        return VH(itemView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {

//        var atpnQesitm = HtmlCompat.fromHtml(items[position].atpnQesitm, HtmlCompat.FROM_HTML_MODE_COMPACT)
//        var efcyQesitm = HtmlCompat.fromHtml(items[position].efcyQesitm, HtmlCompat.FROM_HTML_MODE_COMPACT)
//        var seQesitm = HtmlCompat.fromHtml(items[position].seQesitm, HtmlCompat.FROM_HTML_MODE_COMPACT)
//        var useMethodQesitm = HtmlCompat.fromHtml(items[position].useMethodQesitm, HtmlCompat.FROM_HTML_MODE_COMPACT)

        holder.binding.maker.text = "업체명 : " + items[position].entpName
        holder.binding.name.text = "제품명 : " + items[position].itemName

        Glide.with(context).load(items[position].itemImage).into(holder.binding.image)

        holder.itemView.setOnClickListener {

            val intent = Intent(context, MedicalMoreActivity::class.java)

            intent.putExtra("entpName", items[position].entpName)
            intent.putExtra("itemName", items[position].itemName)
            intent.putExtra("atpnQesitm", items[position].atpnQesitm)
            intent.putExtra("efcyQesitm", items[position].efcyQesitm)
            intent.putExtra("itemImage", items[position].itemImage)
            intent.putExtra("seQesitm", items[position].seQesitm)
            intent.putExtra("useMethodQesitm", items[position].useMethodQesitm)

            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity, Pair(holder.binding.image,"zzz"))

            context.startActivity(intent, optionsCompat.toBundle())

        }

    }

    override fun getItemCount(): Int = items.size
}