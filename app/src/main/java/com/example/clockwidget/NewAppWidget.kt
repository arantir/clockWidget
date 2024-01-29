package com.example.clockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Looper
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
        //Toast.makeText(context, "onEnabled", Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

val handler = android.os.Handler(Looper.getMainLooper())
/*
val r: Runnable = object : Runnable {
    override fun run() {
        handler.postDelayed(this, 1000)
        gameOver()
    }
}
*/
lateinit var views : RemoteViews
lateinit var appWidgetManager: AppWidgetManager
var appWidgetId: Int = 0

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    //оповещения для проверки жизнеспособности таймера
    //Toast.makeText(context, "---", Toast.LENGTH_SHORT).show()

    // Construct the RemoteViews object
    //val views = RemoteViews(context.packageName, R.layout.new_app_widget)

    // Текущее время
    //views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))

    // Instruct the widget manager to update the widget
    //appWidgetManager.updateAppWidget(appWidgetId, views)
    views = RemoteViews(context.packageName, R.layout.new_app_widget)
    com.example.clockwidget.appWidgetManager = appWidgetManager
    com.example.clockwidget.appWidgetId = appWidgetId

    setTimeLabel()

    handler.postDelayed(
        {
            setTimeLabel()
        }, 5000)
}

fun setTimeLabel(){

    // Текущее время
    views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

