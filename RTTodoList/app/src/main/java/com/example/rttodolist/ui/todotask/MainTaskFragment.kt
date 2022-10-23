package com.example.rttodolist.ui.todotask

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.rttodolist.ClientToken
import com.example.rttodolist.MainActivity
import com.example.rttodolist.R
import com.example.rttodolist.ui.todo.Task
import com.example.rttodolist.ui.todo.taskId
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class MainTaskFragment : Fragment() {
    val BASE_URL = "http://507215.msk-kvm.ru"
    val JSON: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
    val client = OkHttpClient()
    var isDone = true
    var task: Task = taskId.task!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = CheckBoxAdapter(requireContext(), task.points)
        val convertView = inflater.inflate(R.layout.fragment_main_task, container, false)
        convertView.findViewById<TextView>(R.id.taskViewTitle).text = task.title
        convertView.findViewById<TextView>(R.id.taskViewDescription).text = task.description
        convertView.findViewById<TextView>(R.id.taskViewDate).text = task.deadline
        convertView.findViewById<TextView>(R.id.taskViewExecutor).text = task.executor_id
        convertView.findViewById<ListView>(R.id.taskList).adapter = adapter
        return convertView
    }
}