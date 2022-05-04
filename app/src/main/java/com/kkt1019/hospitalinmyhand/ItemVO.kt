package com.kkt1019.hospitalinmyhand

import com.google.gson.annotations.SerializedName

public class ItemVO {

    var no = 0
    var useremail: String? = null
    var profile: String? = null
    var uniqueid: String? = null
    var message: String? = null
    var picture: String? = null
    var date: String? = null

    public fun ItemVO(no: Int, useremail: String?, profile: String?, uniqueid: String?, message: String?, picture: String?, date: String?) {
        this.no = no
        this.useremail = useremail
        this.profile = profile
        this.uniqueid = uniqueid
        this.message = message
        this.picture = picture
        this.date = date
    }

    fun ItemVO() {

    }

}