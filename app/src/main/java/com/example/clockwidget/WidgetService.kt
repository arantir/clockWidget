package com.example.clockwidget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast

class WidgetService: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //wake the device
        WakeLocker.acquire(context)

        //Increase the number in the widget
        val preferences : SharedPreferences = context.getSharedPreferences("PREFS", 0)
        var value : Int = preferences.getInt("value", 1)
        value++
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putInt("value", value)
        editor.apply()

        //force widget update
        val widgetIntent : Intent = Intent(context, NewAppWidget::class.java)
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val ids: IntArray = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context, NewAppWidget::class.java))
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.sendBroadcast(widgetIntent)

        Log.d("WIDGET", "Widget set to update!")

        //go back to sleep
        WakeLocker.realize()
    }

}