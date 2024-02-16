package com.kkt1019.hospitalinmyhand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.adapter.ReviewAdapter
import com.kkt1019.hospitalinmyhand.databinding.ActivityMyReviewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReview : AppCompatActivity() {

    var items = mutableListOf<ItemVO>()

    val recycler:RecyclerView by lazy { binding.recycler}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    val binding:ActivityMyReviewBinding by lazy { ActivityMyReviewBinding.inflate(layoutInflater) }

    override fun onResume() {
        super.onResume()

        datas()
    }

    fun datas(){

        //서버에서 데이터를 불러오는 기능 메소드
        val retrofit = RetrofitHelper.getRetrofitInstanceGson2()
        val retrofitService = retrofit!!.create(MyRetrofitService::class.java)
        val call = retrofitService.loadDataFromServer(G.nickname!!)
        call.enqueue(object : Callback<ArrayList<ItemVO?>> {
            override fun onResponse(call: Call<ArrayList<ItemVO?>>, response: Response<ArrayList<ItemVO?>>) {
                items.clear()
                binding.recycler.adapter?.notifyDataSetChanged()

                val list = response.body()!!
                Log.i("yyy", response.body().toString())
                //response null? why?
                for (ItemVO in list) {
                    if (ItemVO != null) {

                        items.add(0, ItemVO)

                        Toast.makeText(this@MyReview, G.nickname, Toast.LENGTH_SHORT).show()

                    }
                    binding.recycler.adapter?.notifyItemInserted(0)
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemVO?>>, t: Throwable) {
//                Toast.makeText(context, "error : " + t.message, Toast.LENGTH_SHORT).show()

                //확인
                AlertDialog.Builder(this@MyReview).setMessage(t.message).create().show()
            }
        })

        recycler.adapter = ReviewAdapter(this, items)

    }
}