package com.example.passwordmanager

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.databinding.ActivityAddPasswordBinding
import org.json.JSONArray
import org.jsoup.Jsoup

class AddPassword : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAddPasswordBinding
    private lateinit var appPreferences: SharedPreferences
    private lateinit var appPreferencesEdit: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        // удаляем рамку
        makeFullScreen()
        // привязывааем view
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)
    }

    private fun makeFullScreen() {
        supportActionBar?.hide()
    }

    fun addPassword(view:View) {
        val add1: EditText = findViewById(R.id.editTextTextPersonName)
        val add2: EditText = findViewById(R.id.editTextTextPassword)
        appPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE)
        val add3 = appPreferences.getString("userId", "")
        val encrypt = AES()
        val data = encrypt.encrypt(add2.getText().toString())
        val connectString = "http://danilsn9.beget.tech/add.php"
        Thread {
            // get categories from internet and set it into shared preferences
            val jsonString =
                Jsoup.connect(connectString).data("id", add3.toString()).data("prname", add1.getText().toString()).data("password", data)
                    .post().body()
            Handler(Looper.getMainLooper()).post {

                    val text = "Пароль добавлен"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                    val startMain = Intent(this, MainActivity::class.java)
                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(startMain)
                    finish()
            }
        }.start()
    }

}