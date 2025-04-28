package com.example.clockwidget

//import android.R
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity(), ColorPickerDialogListener {

    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Покраска статусной строки.(Это та бесячая фиолетовая полоска сверху)
        window.statusBarColor = ContextCompat.getColor(this, R.color.grey)

        //Конструктор сообщения для кнопки о программе.
        /*
        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
        builder.setMessage(R.string.about_text)
        builder.setPositiveButton(android.R.string.yes) { dialog, which -> 0 }
        builder.show()
        */

        //Нажатие на кнопку выбор цвета
        findViewById<Button>(R.id.btColorPick).setOnClickListener {
            //Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            createColorPickerDialog(1)
        }

    }

    private fun createColorPickerDialog(id: Int) {
        ColorPickerDialog.newBuilder()
            .setColor(Color.RED)
            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
            .setAllowCustom(true)
            .setAllowPresets(true)
            .setColorShape(ColorShape.SQUARE)
            .setDialogId(id)
            .show(this)
    }

    //Действие при выборе цвета.
    override fun onColorSelected(dialogId: Int, color: Int) {
        //Красим то что в главном экране приложения.
        findViewById<TextView>(R.id.textClock).setTextColor(color)
        findViewById<TextView>(R.id.textClockDate).setTextColor(color)

        //Красим то что в виджете.
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
        val thisWidget = ComponentName(context, NewAppWidget::class.java)
        //remoteViews.setTextViewText(R.id.appwidget_text, "myText" + System.currentTimeMillis())
        remoteViews.setInt(R.id.textClockWidget, "setTextColor", color)
        remoteViews.setInt(R.id.textClockDateWidget, "setTextColor", color)
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }

    override fun onDialogDismissed(dialogId: Int) {
        //Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
    }

}
