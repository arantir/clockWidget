package com.example.clockwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.RemoteViews
import android.widget.Toast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask


const val WIDGET_SYNC = "WIDGET_SYNC"
var timer = Timer()
var blockTimer: Boolean = true

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

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
    }

    override fun onReceive(context: Context, intent: Intent?) {

        if (WIDGET_SYNC == intent?.action)
        {
            val appWidgetId = intent.getIntExtra("appWidgetId", 0)
            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
        }

        super.onReceive(context, intent)
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val intent = Intent(context, NewAppWidget::class.java)
    intent.action = WIDGET_SYNC
    intent.putExtra("appWidgetId", appWidgetId)
    var views = RemoteViews(context.packageName, R.layout.new_app_widget)

    if(blockTimer)
    {
        blockTimer = false
        //Таймер для регулярного обновления.
        val handler = Handler()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable {
                    // send a broadcast to the widget.

                    // Construct the RemoteViews object
                    //views = RemoteViews(context.packageName, R.layout.new_app_widget)

                    // Текущее время
                    views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))
                    // Instruct the widget manager to update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                })
            }
        }
        timer = Timer()
        timer.scheduleAtFixedRate(task, 0, 1000) // Executes the task every 1 seconds.
    }

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

