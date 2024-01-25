package com.example.clockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {


    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)

        //get the widget value
        val preferences : SharedPreferences = context.getSharedPreferences("PREFS", 0)
        val value : Int = preferences.getInt("value", 1)

        //set the value in the textview
        views.setTextViewText(R.id.appwidget_text, "$value")

        //Toast.makeText(context, "$value", Toast.LENGTH_SHORT).show()

        //update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)

        //reschedule the widget refresh
        val alarmHandler : AlarmHandler = AlarmHandler(context)
        alarmHandler.cancelAlarmManager()
        alarmHandler.setAlarmManager()

        Log.d("WIDGET", "Widget updated!")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled

        //stop updating the widget
        val alarmHandler : AlarmHandler = AlarmHandler(context)
        alarmHandler.cancelAlarmManager()

        Log.d("WIDGET", "Widget removed!")
    }

}

/*
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // send a broadcast to the widget.

    // Construct the RemoteViews object
    var views = RemoteViews(context.packageName, R.layout.new_app_widget)

    //Инициализируем и запускаем таймер 1 раз.
    if(blockTimer)
    {
        blockTimer = false
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {

                //оповещения для проверки жизнеспособности таймера
                //Toast.makeText(context, "---", Toast.LENGTH_SHORT).show()

                // Текущее время
                views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views)

                mainHandler.postDelayed(this, 1000)
            }
        })
    }

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)

}
*/

