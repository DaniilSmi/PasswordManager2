package com.example.passwordmanager

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class WhatchPassword : AppCompatActivity() {
    private lateinit var appPreferences: SharedPreferences
    private lateinit var appPreferencesEdit: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatch_password)
        makeFullScreen()
        appPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE)
        val data = JSONArray(appPreferences.getString("userData", ""))
        val position = intent.getStringExtra("id23")!!.toInt()
        val pr_name = data.getJSONObject(position).getString("program_name")
        val password = data.getJSONObject(position).getString("password")
        val whatch1: TextView = findViewById(R.id.textTextPersonName)
        val whatch2: TextView = findViewById(R.id.textTextPassword)

        val encrypt = AES()

        whatch1.text = pr_name
        whatch2.text = encrypt.decrypt(password)
    }

    private fun makeFullScreen() {
        supportActionBar?.hide()
    }
}