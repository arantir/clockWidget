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

    val handler = android.os.Handler(Looper.getMainLooper())

    private lateinit var views : RemoteViews
    private lateinit var appWidgetManager: AppWidgetManager
    private var appWidgetId: Int = 0

    private val r: Runnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, 5000)
            setTimeLabel()
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        //оповещения для проверки жизнеспособности таймера
        //Toast.makeText(context, "---", Toast.LENGTH_SHORT).show()

        views = RemoteViews(context.packageName, R.layout.new_app_widget)
        this.appWidgetManager = appWidgetManager
        this.appWidgetId = appWidgetId

        setTimeLabel()

        handler.postDelayed(r, 5000)
    }

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

    fun setTimeLabel(){

        // Текущее время
        views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}





