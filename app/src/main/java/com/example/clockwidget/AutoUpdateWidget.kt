package com.example.clockwidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.os.Handler
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AutoUpdateWidget {

    var blockTimer: Boolean = true

    fun startTimer(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int)
    {
        blockTimer = false
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)
        val mainHandler = Handler()
        mainHandler.post(object : Runnable {
            override fun run() {

                //оповещения для проверки жизнеспособности таймера
                //Toast.makeText(context, "---", Toast.LENGTH_SHORT).show()

                // Текущее время
                views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(
                    Date()
                ))
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views)

                mainHandler.postDelayed(this, 1000)
            }
        })
    }

}