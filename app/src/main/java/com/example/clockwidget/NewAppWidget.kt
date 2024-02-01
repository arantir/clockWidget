package com.example.clockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget : AppWidgetProvider() {

    private lateinit var views : RemoteViews
    private lateinit var appWidgetManager: AppWidgetManager
    private var appWidgetId: Int = 0

    companion object {

        val ACTION_AUTO_UPDATE = "AUTO_UPDATE"

        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            val widgetText = Random.nextInt(0, 100).toString()

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_text)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?)
    {
        super.onReceive(context, intent)
        // Do something

        if (intent!!.action == ACTION_AUTO_UPDATE) {
            // DO SOMETHING
            setTimeLabel()
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        //оповещения для проверки жизнеспособности
        //Toast.makeText(context, "---", Toast.LENGTH_SHORT).show()

        views = RemoteViews(context.packageName, R.layout.new_app_widget)
        this.appWidgetManager = appWidgetManager
        this.appWidgetId = appWidgetId

        //setTimeLabel()

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
        Toast.makeText(context, "onEnabled", Toast.LENGTH_SHORT).show()

        // start alarm
        val appWidgetAlarm = AppWidgetAlarm(context.applicationContext)
        appWidgetAlarm.startAlarm()
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Toast.makeText(context, "onDisabled", Toast.LENGTH_SHORT).show()

        // stop alarm only if all widgets have been disabled
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName)

        if (appWidgetIds.isEmpty()) {
            // stop alarm
            val appWidgetAlarm = AppWidgetAlarm(context.getApplicationContext())
            appWidgetAlarm.stopAlarm()
        }
    }

    fun setTimeLabel(){
        // Текущее время
        views.setTextViewText(R.id.appwidget_text, SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}





