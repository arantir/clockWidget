package com.example.clockwidget

//import android.R
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape


class MainActivity : AppCompatActivity(), ColorPickerDialogListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    override fun onColorSelected(dialogId: Int, color: Int) {
        findViewById<TextView>(R.id.tvExample).setTextColor(color)
        //findViewById<TextView>(R.id.appwidget_text).setTextColor(color)

        val context: Context = this
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
        val thisWidget = ComponentName(context, NewAppWidget::class.java)
        //remoteViews.setTextViewText(R.id.appwidget_text, "myText" + System.currentTimeMillis())
        remoteViews.setInt(R.id.appwidget_text, "setTextColor", color)
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }

    override fun onDialogDismissed(dialogId: Int) {
        //Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
    }

}
