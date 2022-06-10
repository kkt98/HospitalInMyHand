package com.kkt1019.hospitalinmyhand

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

        setSupportActionBar(binding.toolbar)

        binding.toolbar.title = "약 검색"



        binding.btn.setOnClickListener { datas() }
    }

    var items1 = mutableListOf<MedicalItems>()
    var items2 = mutableListOf<MedicalItems>()

    fun datas(){

        var edit = binding.edit.text.toString()

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val call = retrofitService.MedicalData("H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==", 1, 100, "json", "$edit")

        call.enqueue(object : Callback<MedicalItemVO> {
            override fun onResponse(call: Call<MedicalItemVO>, response: Response<MedicalItemVO>) {

                val medicalResponse: MedicalItemVO? = response.body()
                Log.i("ccc", medicalResponse?.body?.items?.size.toString())

                medicalResponse?.body?.items?.let {

                    items1.addAll(it)

                    Log.i("jjj", items1.toString())

                    binding.recycler.adapter = MedicalAdapter(this@MedicalActivity, items1)

                }
            }

            override fun onFailure(call: Call<MedicalItemVO>, t: Throwable) {
                AlertDialog.Builder(this@MedicalActivity).setMessage("error : ${t.message}").create().show()
            }

        })

    }

//    fun searchMedical(){
//
//        var etname =  binding.edit.text.toString()
//
//        for (item in items1) {
//            Log.i("aaa", item.itemName)
//            if (item.itemName.contains(etname)) {
//                items2.add(item)
//            }
//        }
//
//        binding.edit.text.clear()
//
//        binding.recycler.adapter = MedicalAdapter(this@MedicalActivity, items2)
//
//    }
}