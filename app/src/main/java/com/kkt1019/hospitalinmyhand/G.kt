package com.kkt1019.hospitalinmyhand

import com.applandeo.materialcalendarview.EventDay
import java.util.ArrayList

class G {
    companion object{

        var nickname: String? = null
        var profileUrl: String? = null
        var uniqueid:String? = null
        lateinit var Xpos : String
        lateinit var Ypos : String
        var location: String? = null
        var myschedule: String? = null
        var days:String? = null

        val events = ArrayList<EventDay>()
    }
}