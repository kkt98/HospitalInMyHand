package com.kkt1019.hospitalinmyhand

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.kkt1019.hospitalinmyhand.databinding.ActivityRevieEditBinding
import com.kkt1019.hospitalinmyhand.network.RetrofitHelper
import com.kkt1019.hospitalinmyhand.network.RetrofitService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File

class RevieEdit : AppCompatActivity() {

    val binding : ActivityRevieEditBinding by lazy { ActivityRevieEditBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSelect.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        binding.btnSave.setOnClickListener { saveClick() }
    }

    ////////////////이미지 선택//////////////////////////////

    var imgPath: String? = null

    var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data!!.data
            Glide.with(this).load(uri).into(binding.iv)
            imgPath = getRealPathFromUri(uri!!)
            //확인
            AlertDialog.Builder(this).setMessage(imgPath).create().show()
        }
    }

    fun getRealPathFromUri(uri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, uri, proj, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result
    }
    ////////////////이미지 선택//////////////////////////////

    private fun saveClick(){

        val message = binding.etMsg.text.toString()
        val useremail = G.nickname
        val uniqueid = G.uniqueid
        val profile = G.profileUrl

        //레트로핏 작업
        val retrofit: Retrofit? = RetrofitHelper.getRetrofitInstanceScalars()
        val retrofitService = retrofit?.create(RetrofitService::class.java)


        //1) 이미지파일을 MultiPartBody.Part 로 포장 : @Part
        var filePart: MultipartBody.Part? = null
        if (imgPath != null) {
            val file = File(imgPath)
            val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
            filePart = MultipartBody.Part.createFormData("img", file.name, requestBody)
        }

        val dataPart = HashMap<String, String>()
        dataPart.put("message", message)
        if (useremail != null) {
            dataPart.put("useremail", useremail)
        }
        dataPart.put("uniqueid", uniqueid.toString())
        dataPart.put("profile", profile.toString())

        val call = retrofitService!!.postDataToServer(dataPart, filePart)
        call!!.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                val s = response.body()
                Toast.makeText(this@RevieEdit, s + "", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
//                Toast.makeText(this@RevieEdit, "error : " + t.message, Toast.LENGTH_SHORT).show()

                var builder = AlertDialog.Builder(this@RevieEdit)
                builder.setMessage(t.message).show()
            }
        })

    }

}