package com.kkt1019.hospitalinmyhand

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.databinding.ActivityCalendarBinding
import com.kkt1019.hospitalinmyhand.databinding.AlertdialogEdittextBinding
import java.util.*


class CalendarActivity : AppCompatActivity() {

    val binding:ActivityCalendarBinding by lazy { ActivityCalendarBinding.inflate(layoutInflater) }

    val recycler: RecyclerView by lazy { binding.calendarRecycler }

    var items = mutableListOf<Calendar_Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.calendar.setOnDayClickListener {

            val builder = AlertDialog.Builder(this)
            val builderItem = AlertdialogEdittextBinding.inflate(layoutInflater)
            val editText = builderItem.editText
            with(builder){
                setMessage("일정 입력")
                setView(builderItem.root)
                setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    if(editText.text != null) Toast.makeText(this@CalendarActivity, "일정 : ${editText.text}", Toast.LENGTH_SHORT).show()
                    G.myschedule = editText.text.toString()
                    items.add(Calendar_Item(editText.text) )
//                    val myEventDay = MyEventDay(binding.calendar.getSelectedDate(),
//                        R.drawable.btn_dialog, noteEditText.getText().toString()
//                    )
                }
                setNegativeButton("취소"){dialogInterface: DialogInterface, i: Int ->
                    return@setNegativeButton
                }
                builder.setOnDismissListener { binding.calendarRecycler.adapter?.notifyDataSetChanged() }
                show()
            }

        }

//        binding.exThreeAddButton.setOnClickListener {
//
////            var size = binding.calendarView.selectedDates.size-1
////            Log.i("ddd", binding.calendarView.selectedDates[size].toString())
//
//
//
//
//        }







        recycler.adapter = CalendarAdapter(this, items)

    }


}