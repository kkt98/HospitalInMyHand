package com.kkt1019.hospitalinmyhand.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.applandeo.materialcalendarview.EventDay
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.adapter.CalendarAdapter
import com.kkt1019.hospitalinmyhand.databinding.ActivityCalendarBinding
import com.kkt1019.hospitalinmyhand.databinding.AlertdialogEdittextBinding
import com.kkt1019.hospitalinmyhand.roomdata.CalendarDatabase
import com.kkt1019.hospitalinmyhand.roomdata.CalendarEntity
import kotlinx.coroutines.*
import java.util.Calendar

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var adapter: CalendarAdapter
    private var calendarEntities = mutableListOf<CalendarEntity>()
    private var selectedDate = Calendar.getInstance().timeInMillis // 현재 날짜를 기본값으로 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "일정"

        setupRecyclerView()
        loadEventsAndMarkDates()

        //캘린더 날자 선택 이벤트
        binding.calendar.setOnDayClickListener { eventDay ->
            selectedDate = eventDay.calendar.timeInMillis
            updateRecyclerViewForDate(selectedDate)
        }
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
                addEventToDatabase(selectedDate, content)
            }
            setNegativeButton("취소", null)
            show()
        }
    }

    //일정 RoomDataBase에 추가
    private fun addEventToDatabase(date: Long, content: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val newEvent = CalendarEntity(date = date, content = content, markerIcon = R.drawable.ic_light_off) // 마커 아이콘 수정
            CalendarDatabase.getDatabase(applicationContext).calendarDao().insertEvent(newEvent)

            loadEventsAndMarkDates() // 이벤트 추가 후 날짜별 이벤트를 다시 로드
        }
    }


    //일정이 저장된 날자엔 표시 해주기
    private fun loadEventsAndMarkDates() {
        CoroutineScope(Dispatchers.IO).launch {
            val events = CalendarDatabase.getDatabase(applicationContext).calendarDao().getAllEvents()
            val eventDays = ArrayList<EventDay>()

            events.forEach { calendarEntity ->
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = calendarEntity.date
                }
                val eventDay = EventDay(calendar, calendarEntity.markerIcon!!)
                eventDays.add(eventDay)
            }

            withContext(Dispatchers.Main) {
                binding.calendar.setEvents(eventDays)
            }
        }
    }

    //일정이 저장된 날자 선택시 일정 리사이클러뷰에 보여주기
    private fun updateRecyclerViewForDate(date: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val events = CalendarDatabase.getDatabase(applicationContext).calendarDao().getEventsByDate(date)
            withContext(Dispatchers.Main) {
                adapter.updateItems(events)
            }
        }
    }
}
