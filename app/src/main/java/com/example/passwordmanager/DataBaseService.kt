package com.example.passwordmanager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import org.jsoup.Jsoup


class DataBaseService : Service(){



    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }


}