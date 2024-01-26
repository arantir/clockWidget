package com.example.clockwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Calendar
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
    //оповещения для проверки жизнеспособности таймера
    //Toast.makeText(context, "---", Toast.LENGTH_SHORT).show()

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)


    val clockIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, clockIntent, PendingIntent.FLAG_IMMUTABLE)


    views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)

    // Текущее время
    views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)

}


