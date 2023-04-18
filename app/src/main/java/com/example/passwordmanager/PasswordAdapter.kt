package com.example.passwordmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class PasswordAdapter(private val names: JSONArray, private val listener: Listener) : RecyclerView.Adapter<PasswordAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // загружем элементы в кеш приложения
        val namePassword: TextView = itemView.findViewById(R.id.namePassword)
        val watchPassword: Button = itemView.findViewById(R.id.buttonSale2)
        val deletePassword: Button = itemView.findViewById(R.id.buttonSale3)
    }

    // подключаем визуал
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.password_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // привязываем view
        holder.namePassword.text = names.getJSONObject(position).getString("program_name")

        //добавляем слушатели нажатий на кнопки
        holder.watchPassword.setOnClickListener {
            listener.onClickWatch(position.toString())
        }
        holder.deletePassword.setOnClickListener {
            listener.onClickDelete(names.getJSONObject(position).getString("id"))
        }
    }

    override fun getItemCount() = names.length()

    // интерфейс для связи с активити
    interface Listener {
        // функции для обработки нажатий
        fun onClickWatch(position: String)
        fun onClickDelete(position: String)
    }
}