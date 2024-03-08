package com.kkt1019.hospitalinmyhand.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.applandeo.materialcalendarview.EventDay
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.adapter.CalendarAdapter
import com.kkt1019.hospitalinmyhand.databinding.ActivityCalendarBinding
import com.kkt1019.hospitalinmyhand.databinding.AlertdialogEdittextBinding
import com.kkt1019.hospitalinmyhand.roomdatabase.calender.CalendarDatabase
import com.kkt1019.hospitalinmyhand.roomdatabase.calender.CalendarEntity
import com.kkt1019.hospitalinmyhand.viewmodel.CalenderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.Calendar

@AndroidEntryPoint
class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var adapter: CalendarAdapter
    private var calendarEntities = mutableListOf<CalendarEntity>()
    private var selectedDate = Calendar.getInstance().timeInMillis // 현재 날짜를 기본값으로 설정

    private val calenderViewModel: CalenderViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar)
        setContentView(binding.root)
        binding.activity = this

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "일정"

        setupRecyclerView()

        //캘린더 날자 선택 이벤트
        binding.calendar.setOnDayClickListener { eventDay ->
            selectedDate = eventDay.calendar.timeInMillis

            //일정이 저장된 날자 선택시 일정 리사이클러뷰에 보여주기
            calenderViewModel.updateRecyclerViewForDate(selectedDate)
        }

        calenderViewModel.eventsList.observe(this, Observer {
            adapter.updateItems(it)
        })

        //일정이 있는 날자에 마커 표시
        calenderViewModel.loadEventsAndMarkDates()
        calenderViewModel.eventsMaker.observe(this@CalendarActivity, Observer {
            val eventDays = ArrayList<EventDay>()
            it.forEach { calendarEntity ->
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = calendarEntity.date
                }
                val eventDay = EventDay(calendar, calendarEntity.markerIcon!!)
                eventDays.add(eventDay)
            }
            binding.calendar.setEvents(eventDays)
        })
    }

    private fun setupRecyclerView() {
        adapter = CalendarAdapter(this, calendarEntities)
        binding.calendarRecycler.adapter = adapter
    }

    //다이얼로그 실행
    fun showAddEventDialog() {
        val builderItem = AlertdialogEdittextBinding.inflate(layoutInflater)
        AlertDialog.Builder(this).apply {
            setMessage("일정 입력")
            setView(builderItem.root)
            setPositiveButton("확인") { _, _ ->
                val content = builderItem.editText.text.toString()
                // selectedDate를 사용하여 데이터베이스에 이벤트 추가
                calenderViewModel.addEventToDatabase(selectedDate, content)
//                addEventToDatabase(selectedDate, content)
            }
            setNegativeButton("취소", null)
            show()
        }
    }
}
