package com.kkt1019.hospitalinmyhand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.databinding.ActivityMedicalBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicalActivity : AppCompatActivity() {
    val binding: ActivityMedicalBinding by lazy { ActivityMedicalBinding.inflate(layoutInflater) }

    val recycler: RecyclerView by lazy { binding.recycler }

//    var items = mutableListOf<MedicalItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "약 검색"

        datas()
    }

    fun datas(){

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
//        val call = retrofitService.MedicalData("H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==", 1, 10, "json")
////        val call = retrofitService.aaa()
////        val call = retrofitService.bbb(1,3,"json")
//
//        call.enqueue(object : Callback<MedicalItemVO> {
//            override fun onResponse(call: Call<MedicalItemVO>, response: Response<MedicalItemVO>) {
//
//                val medicalResponse: MedicalItemVO? = response.body()
//                Log.i("ccc", medicalResponse?.items?.size.toString())
//
//                medicalResponse?.items?.let {
//                    Log.i("ccc", it.size.toString())
//                    binding.recycler.adapter = MedicalAdapter(this@MedicalActivity, it)
//                }
//            }
//
//            override fun onFailure(call: Call<MedicalItemVO>, t: Throwable) {
//                AlertDialog.Builder(this@MedicalActivity).setMessage("error : ${t.message}").create().show()
//            }
//
//
//        })
        val call2 = retrofitService.MedicalDataString("H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==", 1, 10, "json")
//        val call2 = retrofitService.aaa()
//        val call2 = retrofitService.bbb(1, 5, "json")

        call2.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

                AlertDialog.Builder(this@MedicalActivity).setMessage("${response.body()}").create().show()

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                AlertDialog.Builder(this@MedicalActivity).setMessage("error : ${t.message}").create().show()
            }

        })






//        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
//        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
//        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
//        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
//        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
//
//        recycler.adapter = MedicalAdapter(this, items)

    }
}