package com.example.passwordmanager

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.jsoup.Jsoup

class LoginActivity : AppCompatActivity() {
    // переменная для проверки введённых данных
    private var checkLogin:Boolean = false
    private lateinit var button: Button
    private lateinit var appPreferences: SharedPreferences
    private lateinit var appPreferencesEdit: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        makeFullScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
    }

    private fun checkForData () {
        val textView: EditText = findViewById(R.id.editTextEmail)
        val textView2: EditText = findViewById(R.id.editTextPassword)
        val connectString = "http://danilsn9.beget.tech/login.php?email=${textView.text}&password=${textView2.text}"
        Thread {
            // get categories from internet and set it into shared preferences
            val jsonString =
                Jsoup.connect(connectString).ignoreContentType(true).execute().body()
            Handler(Looper.getMainLooper()).post {
                // some code
                if(jsonString != "Passwords isn`t the same" && jsonString != "User not found") {
                    appPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE)
                    appPreferencesEdit = appPreferences.edit()
                    appPreferencesEdit.putString("userId", jsonString)
                    appPreferencesEdit.apply() // save
                    val startMain = Intent(this, MainActivity::class.java)
                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(startMain)
                    finish()
                }

            }
        }.start()

    }

    fun login(view: View) {
        checkForData()
    }

    private fun makeFullScreen() {
        supportActionBar?.hide()
    }
}