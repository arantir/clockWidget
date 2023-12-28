package com.example.clockwidget

import android.content.Context
import android.os.PowerManager

abstract class WakeLocker {
    private var wakeLock : PowerManager.WakeLock? = null

    fun acquire(context: Context){
        if (wakeLock != null){
            wakeLock?.release()
        }

        val powerManager: PowerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK or
                PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE, "WIDGET: Wake lock acquired!")
        wakeLock?.acquire(2000)
    }
    fun realize(){
        if (wakeLock != null){
            wakeLock?.release()
        }
        wakeLock = null
    }
}