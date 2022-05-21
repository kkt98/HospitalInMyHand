package com.kkt1019.hospitalinmyhand

import kotlin.properties.Delegates

class G {
    companion object{

        var nickname: String? = null
        var profileUrl: String? = null
        var uniqueid:String? = null
        var Xpos by Delegates.notNull<Double>()
        var Ypos by Delegates.notNull<Double>()
        var location : String = ""

    }
}