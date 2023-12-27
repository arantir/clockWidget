package com.example.clockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//val autoUpdateWidget: AutoUpdateWidget = AutoUpdateWidget()
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

}

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

