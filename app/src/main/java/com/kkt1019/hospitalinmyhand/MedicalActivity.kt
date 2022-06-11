package com.kkt1019.hospitalinmyhand

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.databinding.ActivityMedicalBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicalActivity : AppCompatActivity() {
    val binding: ActivityMedicalBinding by lazy { ActivityMedicalBinding.inflate(layoutInflater) }

    val recycler: RecyclerView by lazy { binding.recycler }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.toolbar.title = "의약품 검색"

        binding.btn.setOnClickListener { datas() }
    }

    fun datas(){

        binding.progress.visibility = View.VISIBLE

        val etname = binding.edit.text.toString()

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val call = retrofitService.MedicalData("H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==", "$etname", "json")

        call.enqueue(object : Callback<MedicalItemVO> {
            override fun onResponse(call: Call<MedicalItemVO>, response: Response<MedicalItemVO>) {

                val medicalResponse: MedicalItemVO? = response.body()
                Log.i("ccc", medicalResponse?.body?.items?.size.toString())

                medicalResponse?.body?.items?.let {

                    binding.recycler.adapter = MedicalAdapter(this@MedicalActivity, it)

                    binding.edit.text.clear()

                    binding.progress.visibility = View.GONE

                }
            }

            override fun onFailure(call: Call<MedicalItemVO>, t: Throwable) {
                AlertDialog.Builder(this@MedicalActivity).setMessage("error : ${t.message}").create().show()
            }

        })

    }
}