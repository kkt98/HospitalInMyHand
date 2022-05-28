package com.kkt1019.hospitalinmyhand

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.material.drawable.DrawableUtils
import com.kkt1019.hospitalinmyhand.databinding.ActivityCalendarBinding
import com.kkt1019.hospitalinmyhand.databinding.AlertdialogEdittextBinding
import java.util.*


class CalendarActivity : AppCompatActivity() {

    val binding:ActivityCalendarBinding by lazy { ActivityCalendarBinding.inflate(layoutInflater) }

    val recycler: RecyclerView by lazy { binding.calendarRecycler }

    var items = mutableListOf<Calendar_Item>()

    val events: MutableList<EventDay> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "일정"


        binding.calendar.setOnDayClickListener {

            var size = binding.calendar.selectedDates.size-1
            Log.i("ddd", binding.calendar.selectedDates[size].toString())

            val calendar = Calendar.getInstance()
            events.add(EventDay(calendar, com.kkt1019.hospitalinmyhand.DrawableUtils.getCircleDrawableWithText(this, "M")))
            binding.calendar.setEvents(events)
        }

        binding.exThreeAddButton.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val builderItem = AlertdialogEdittextBinding.inflate(layoutInflater)
            val editText = builderItem.editText
            with(builder){
                setMessage("일정 입력")
                setView(builderItem.root)
                setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    if(editText.text != null) Toast.makeText(this@CalendarActivity, "일정 : ${editText.text}", Toast.LENGTH_SHORT).show()
//                    G.myschedule = editText.text.toString()
                    items.add(Calendar_Item(editText.text) )
//                    val myEventDay = MyEventDay(binding.calendar.getSelectedDate(),
//                        R.drawable.btn_dialog, noteEditText.getText().toString()
//                    )

//                    val calendar1 = Calendar.getInstance()
//                    events.add(EventDay(calendar1, R.drawable.ic_dot))
//


                }
                setNegativeButton("취소"){dialogInterface: DialogInterface, i: Int ->
                    return@setNegativeButton
                }
                builder.setOnDismissListener { binding.calendarRecycler.adapter?.notifyDataSetChanged() }
                show()
            }

        }


        binding.calendar.setOnDayClickListener(OnDayClickListener { eventDay: EventDay ->
            Toast.makeText(this, eventDay.calendar.time.toString() + " " + eventDay.isEnabled, Toast.LENGTH_SHORT).show()
        })

        recycler.adapter = CalendarAdapter(this, items)

    }


}