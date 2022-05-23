package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.core.content.ContextCompat
import com.applandeo.materialcalendarview.CalendarUtils

object DrawableUtils {

    fun getCircleDrawableWithText(context: Context?, string: String?): Drawable {
        val background = ContextCompat.getDrawable(context!!, R.drawable.sample_circle)
        val text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 12)
        val layers = arrayOf(background, text)
        return LayerDrawable(layers)
    }

}