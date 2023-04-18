package com.example.passwordmanager

import android.content.*
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.jsoup.Jsoup
import com.example.passwordmanager.AES

class MainActivity : AppCompatActivity(), PasswordAdapter.Listener {
    private lateinit var appPreferences: SharedPreferences
    private lateinit var appPreferencesEdit: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        // удаляем рамку
        makeFullScreen()
        // привязывааем view
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadPasswordsForUser()
        // подключаем recyclerview и его адаптер
    }

    private fun makeFullScreen() {
        supportActionBar?.hide()
    }


    // загружаем пароли из сети и выводим их
    private fun loadPasswordsForUser() {
        appPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE)
        val position = appPreferences.getString("userId", "")
        val connectString = "http://danilsn9.beget.tech/?id=$position"
        Thread {
            // get categories from internet and set it into shared preferences
            val jsonString =
                Jsoup.connect(connectString).ignoreContentType(true).execute().body()
            Handler(Looper.getMainLooper()).post {
                if (jsonString != "error") {
                appPreferencesEdit = appPreferences.edit()
                appPreferencesEdit.putString("userData", jsonString)
                appPreferencesEdit.apply() // save

                val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = PasswordAdapter(JSONArray(appPreferences.getString("userData", "")), this)
                }
            }
        }.start()
    }


    // функция для просмотра пароля
    override fun onClickWatch(position: String) {
        // передача запроса, расшифровка, вывод
        val whachpassword = Intent(this, WhatchPassword::class.java).apply {
            putExtra("id23", position)
        }
        startActivity(whachpassword)

    }

    // перенаправление на добавление пароля
    fun onButtonAdd (view: View) {
        val addPage = Intent(this, AddPassword::class.java)
        startActivity(addPage)
    }

    // функция для удаления пароля
    override fun onClickDelete(position: String) {
        val position2 = appPreferences.getString("userId", "")
        val connectString = "http://danilsn9.beget.tech/delete.php?id=$position&userid=$position2"
        Thread {
            // get categories from internet and set it into shared preferences
            val jsonString =
                Jsoup.connect(connectString).ignoreContentType(true).execute().body()
            Handler(Looper.getMainLooper()).post {

                if (jsonString == "deleted") {
                    val text = "Элемент удалён"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()

                    loadPasswordsForUser()
                    val startMain = Intent(this, MainActivity::class.java)
                    startActivity(startMain)
                    finish()
                }

            }
        }.start()
    }
}