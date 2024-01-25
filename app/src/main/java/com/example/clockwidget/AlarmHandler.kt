package com.example.clockwidget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import java.util.Calendar

class AlarmHandler(context: Context) {
    private var context: Context

    init {
        this.context = context
    }

    fun setAlarmManager(){
        val intent : Intent = Intent(context, WidgetService::class.java)
        val sender : PendingIntent = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_IMMUTABLE)
        val am : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //get current time and add 10 seconds
        val c : Calendar = Calendar.getInstance()
        val l : Long = c.timeInMillis + 10000

        //set the alarm for 10 seconds in the future
        if (am != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, l, sender)
            }
            else
            {
                am.set(AlarmManager.RTC_WAKEUP, l, sender)
            }
        }
    }

    fun cancelAlarmManager(){
        val intent : Intent = Intent(context, WidgetService::class.java)
        val sender : PendingIntent = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_IMMUTABLE)
        val am : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (am != null)
        {
            am.cancel(sender)
        }
    }
}