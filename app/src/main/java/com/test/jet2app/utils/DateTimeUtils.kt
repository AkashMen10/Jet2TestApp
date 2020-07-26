package com.test.jet2app.utils

import android.content.Context
import com.test.jet2app.R
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.Minutes


fun publishedAgo(context: Context,createdDate:String) :String{
    val now = DateTime.now()
    val createdNewDate = DateTime.parse(createdDate)

    val day = Days.daysBetween(createdNewDate,now).days
    val hour = Hours.hoursBetween(createdNewDate,now).hours
    val minute = Minutes.minutesBetween( createdNewDate,now).minutes

    val timeDifference =
        when {
            day > 0 -> "$day${context.getString(R.string.day_suffix)}"
            hour > 0 ->"$hour${context.getString(R.string.hour_suffix)}"
            else -> "$minute${context.getString(R.string.minute_suffix)}"
        }
    return timeDifference
}