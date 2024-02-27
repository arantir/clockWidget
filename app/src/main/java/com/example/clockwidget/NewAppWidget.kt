package com.example.clockwidget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import kotlin.properties.Delegates

const val WIDGET_SYNC = "WIDGET_SYNC"
var blockTimer: Boolean = true

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        /*
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        */

        update(context)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        scheduleUpdate(context)
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent?) {

        if (WIDGET_SYNC == intent?.action)
        {
            //val appWidgetId = intent.getIntExtra("appWidgetId", 0)
            //updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
        }

        super.onReceive(context, intent)
        update(context)
    }

    private fun fixPendingIntentFlags(flags: Int): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        flags or PendingIntent.FLAG_IMMUTABLE
    } else {
        flags
    }

    private fun createBroadcastPendingIntent(context: Context, change: Boolean): PendingIntent {
        val intent = Intent(context, NewAppWidget::class.java)
            .setAction(if (change) ACTION_CHANGE else ACTION_TICK)
        return PendingIntent.getBroadcast(context, 0, intent, fixPendingIntentFlags(PendingIntent.FLAG_UPDATE_CURRENT))
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun scheduleUpdate(context: Context) {
        val alarmManager: AlarmManager = requireNotNull(context.getSystemService())
        //val environment = Module.getEnvironment(context)
        if (alarmManager.canScheduleExactAlarms()) {
            val cal = Calendar.getInstance()
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            cal.add(Calendar.MINUTE, 1)
            val pendingIntent = createBroadcastPendingIntent(context, change = false)
            //environment.scheduleExactAlarm(cal.timeInMillis, pendingIntent)
            alarmManager.setExact(AlarmManager.RTC, cal.timeInMillis, pendingIntent)
        }
    }

    /*
    @RequiresApi(Build.VERSION_CODES.S)
    fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        //val intent = Intent(context, NewAppWidget::class.java)
        //intent.action = WIDGET_SYNC
        //intent.putExtra("appWidgetId", appWidgetId)
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)

        // Текущее время
        views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))
        //Toast.makeText(context, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()), Toast.LENGTH_SHORT).show()

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
        scheduleUpdate(context)
    }
    */


    @RequiresApi(Build.VERSION_CODES.S)
    private fun update(context: Context) {
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)

        // Текущее время
        views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))
        //Toast.makeText(context, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()), Toast.LENGTH_SHORT).show()

        // Instruct the widget manager to update the widget
        AppWidgetManager.getInstance(context).updateAppWidget(ComponentName(context, NewAppWidget::class.java), views)
        scheduleUpdate(context)
    }
}


private const val PREFIX = "com.kazufukurou.nanji"
private const val ACTION_CHANGE = "$PREFIX.change"
private const val ACTION_TICK = "$PREFIX.tick"
