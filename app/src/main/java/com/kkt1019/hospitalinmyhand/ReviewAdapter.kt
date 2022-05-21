package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReviewAdapter(val context: Context, var items: MutableList<ItemVO>) : RecyclerView.Adapter<ReviewAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivProfile : ImageView by lazy { itemView.findViewById(R.id.profile) }
        val tvId : TextView by lazy { itemView.findViewById(R.id.id) }
        val ivIv : ImageView by lazy { itemView.findViewById(R.id.iv) }
        val tvMessage : TextView by lazy { itemView.findViewById(kr.co.prnd.readmore.R.id.message) }
        val tvDate : TextView by lazy { itemView.findViewById(R.id.date) }
        val ivOption : TextView by lazy { itemView.findViewById(R.id.textViewOptions) }

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

        holder.ivOption.setOnClickListener { it ->

            val popup = PopupMenu(holder.ivOption.context, it)
            popup.inflate(R.menu.recyclerview_item_menu)
            popup.setOnMenuItemClickListener { item ->

                when (item.itemId){

                   R.id.delete -> {

                       val num = items[position].no
                       val nickname = G.nickname

                       val retrofit = RetrofitHelper.getRetrofitInstanceGson()
                       val retrofitService = retrofit?.create(RetrofitService::class.java)
                       val call = retrofitService?.DeleteDataFromServer(nickname!!)
                       call!!.enqueue(object : Callback<ArrayList<ItemVO?>> {
                           override fun onResponse(call: Call<ArrayList<ItemVO?>>, response: Response<ArrayList<ItemVO?>>) {

                               val list = response.body()!!
                               Log.i("yyy", response.body().toString())
                               for (ItemVO in list) {
                                   if (ItemVO != null) {

                                       items.add(0, ItemVO)

                                       Toast.makeText(context, G.uniqueid, Toast.LENGTH_SHORT).show()

                                   }

                               }
                           }

                           override fun onFailure(call: Call<ArrayList<ItemVO?>>, t: Throwable) {}
                       })

//                       val builder = AlertDialog.Builder(context)
//                       builder.setMessage("삭제하시겠습니까?")
//                       builder.setPositiveButton("삭제", DialogInterface.OnClickListener { _, _ ->
//
//                       })
//
//                       builder.setNegativeButton("취소", DialogInterface.OnClickListener { _, _ ->
//                           return@OnClickListener
//                       })

                   }

                }

                true
            }
            popup.show()
        }

    }

    override fun getItemCount(): Int = items.size
}