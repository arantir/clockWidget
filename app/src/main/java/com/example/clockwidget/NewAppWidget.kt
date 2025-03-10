package com.example.clockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        // Перебираем все идентификаторы виджетов
        for (appWidgetId in appWidgetIds) {
            // Здесь вы можете задать начальный цвет, если нужно
            updateAppWidget(context, appWidgetManager, appWidgetId, Color.WHITE) // Начальный цвет
        }
    }

    override fun onEnabled(context: Context) {
        // Этот метод вызывается, когда первый виджет создан
    }

    override fun onDisabled(context: Context) {
        // Этот метод вызывается, когда последний виджет удален
    }


    companion object {
        // Метод для обновления виджета
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, color: Int) {
            // Создаем RemoteViews для виджета
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)

            // Устанавливаем цвет текста
            views.setInt(R.id.textClock, "setTextColor", color)
            views.setInt(R.id.dateTextView, "setTextColor", color)

            // Получаем текущее время и дату
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val dateString = dateFormat.format(Date())
            views.setTextViewText(R.id.dateTextView, dateString)

            // Обновляем виджет
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
