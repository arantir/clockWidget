package com.example.clockwidget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.clockwidget.NewAppWidget
import java.util.Calendar

class AppWidgetAlarm(private val context: Context?) {
    private val ALARM_ID = 0
    private val INTERVAL_MILLIS : Long = 10000

    fun startAlarm() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.MILLISECOND, INTERVAL_MILLIS.toInt())

        val alarmIntent = Intent(context, NewAppWidget::class.java).let { intent ->
            //intent.action = AppWidget.ACTION_AUTO_UPDATE
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        with(context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager) {
            setRepeating(AlarmManager.RTC, calendar.timeInMillis, INTERVAL_MILLIS, alarmIntent)
        }
    }

    fun stopAlarm() {
        val alarmIntent = Intent(NewAppWidget.ACTION_AUTO_UPDATE)
        val pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
