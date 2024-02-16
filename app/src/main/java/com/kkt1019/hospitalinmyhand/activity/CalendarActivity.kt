package com.kkt1019.hospitalinmyhand.activity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.kkt1019.hospitalinmyhand.adapter.CalendarAdapter
import com.kkt1019.hospitalinmyhand.CalendarItemVO
import com.kkt1019.hospitalinmyhand.G
import com.kkt1019.hospitalinmyhand.G.Companion.events
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.RetrofitHelper
import com.kkt1019.hospitalinmyhand.RetrofitService
import com.kkt1019.hospitalinmyhand.databinding.ActivityCalendarBinding
import com.kkt1019.hospitalinmyhand.databinding.AlertdialogEdittextBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*


class CalendarActivity : AppCompatActivity() {

    val binding:ActivityCalendarBinding by lazy { ActivityCalendarBinding.inflate(layoutInflater) }

    val recycler: RecyclerView by lazy { binding.calendarRecycler }

    var items = mutableListOf<CalendarItemVO>()

    var calendr = Calendar.getInstance()
    val calender:CalendarView by lazy { binding.calendar }

    val retrofit: Retrofit = RetrofitHelper.calendarRetrofit()
    val retrofitService = retrofit.create(RetrofitService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

      //  var calendr = Calendar.getInstance()

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "일정"

        binding.exThreeAddButton.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val builderItem = AlertdialogEdittextBinding.inflate(layoutInflater)
            val editText = builderItem.editText
            with(builder){
                setMessage("일정 입력")
                setView(builderItem.root)
                setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->


                    if(editText.text != null) Toast.makeText(this@CalendarActivity, "일정 : ${editText.text}", Toast.LENGTH_SHORT).show()
                    G.myschedule = editText.text.toString()
//                    items.add(Calendar_Item(editText.text))

                    //확인버튼을 누르면, 서버DB로 정보를 전송하는 메소드
                    insertDB()

                    events.add(EventDay(calendr, R.drawable.ic_light_off))
                    calender.setEvents(events)

//                    CalendarItemVO()

                }
                setNegativeButton("취소"){dialogInterface: DialogInterface, i: Int ->
                    return@setNegativeButton
                }
//                builder.setOnDismissListener { binding.calendarRecycler.adapter?.notifyDataSetChanged() }
                show()
            }

        }


        binding.calendar.setOnDayClickListener(OnDayClickListener { eventDay: EventDay ->
            var size = binding.calendar.selectedDates.size-1
            Log.i("ddd", binding.calendar.selectedDates[size].toString())

            G.days = eventDay.calendar.time.toString()


//            Toast.makeText(this, eventDay.calendar.time.toString() + ", " + eventDay.isEnabled, Toast.LENGTH_SHORT).show()

            Log.i("dddddddddddddddd",calendr.toString() )
            Log.i("dddddddddddddddd",calendr.time.toString() )

            calendr=eventDay.calendar

            // 달력뷰의 날짜 클릭시, 서버DB에 저장된 일정들을 불러오는 메소드
            loadDB()

        })

        recycler.adapter = CalendarAdapter(this, items)

    }

    private fun insertDB() {

        val email = G.nickname.toString()
        val message = G.myschedule.toString()
        val date = G.days.toString()

        val dataPart = HashMap<String, String>()
        dataPart.put("email", email)
        dataPart.put("message", message)
        dataPart.put("date", date)

        //확인버튼을 누르면, 서버DB로 정보를 전송하는 메소드
        val call: Call<String> = retrofitService.calendarinsert(dataPart)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i("잘 올라갔나", response.body() + "")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("왜 안올라갔어", t.message + "")
            }

        })


    }
    private fun loadDB(){

        //달력뷰의 날짜 클릭시, 서버DB에 저장된 일정들을 불러오는 메소드
        val retrofit = RetrofitHelper.getRetrofitInstanceGson()
        val retrofitService = retrofit!!.create(RetrofitService::class.java)
        val call = retrofitService.calendarLoad(G.nickname.toString(), G.days.toString())
        call.enqueue(object : Callback<ArrayList<CalendarItemVO>>{
            override fun onResponse(
                call: Call<ArrayList<CalendarItemVO>>,
                response: Response<ArrayList<CalendarItemVO>>
            ) {
                items.clear()
                binding.calendarRecycler.adapter?.notifyDataSetChanged()

                val list = response.body()!!
                for (itemm in list) {
                    if (itemm != null) {

                        items.add(0, itemm)

                    }

//                    Toast.makeText(this@CalendarActivity, "$itemm", Toast.LENGTH_SHORT).show()
                    binding.calendarRecycler.adapter?.notifyItemInserted(0)
                }
            }

            override fun onFailure(call: Call<ArrayList<CalendarItemVO>>, t: Throwable) {
                Toast.makeText(this@CalendarActivity, "error : $t", Toast.LENGTH_SHORT).show()
            }

        })

    }








}