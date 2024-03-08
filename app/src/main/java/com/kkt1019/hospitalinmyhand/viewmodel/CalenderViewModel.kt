package com.kkt1019.hospitalinmyhand.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.repository.CalendarRepository
import com.kkt1019.hospitalinmyhand.roomdatabase.calender.CalendarEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalenderViewModel @Inject constructor(
    private val repository: CalendarRepository
) : ViewModel() {

    private val _eventsMaker = MutableLiveData<List<CalendarEntity>>()
    val eventsMaker: LiveData<List<CalendarEntity>> = _eventsMaker

    private val _eventsList = MutableLiveData<List<CalendarEntity>>()
    val eventsList: LiveData<List<CalendarEntity>> = _eventsList

    fun addEventToDatabase(date: Long, content: String) {
        viewModelScope.launch {
            val newEvent = CalendarEntity(date = date, content = content, markerIcon = R.drawable.ic_light_off)
            repository.addEvent(newEvent)
//            loadEventsAndMarkDates()
        }
    }

    fun loadEventsAndMarkDates() {
        viewModelScope.launch {
            val eventList = repository.getAllEvents()
            _eventsMaker.postValue(eventList)
        }
    }

    fun updateRecyclerViewForDate(date: Long) {
        viewModelScope.launch {
            val eventsByDate = repository.getEventsByDate(date)
            _eventsList.postValue(eventsByDate)
        }
    }

}