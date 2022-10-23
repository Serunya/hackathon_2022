package com.example.rttodolist.ui.todotask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.rttodolist.MainActivity
import com.example.rttodolist.R
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(com.example.rttodolist.ui.todo.taskId.task == null)
            startActivity(Intent(this,MainActivity::class.java))
        setContentView(R.layout.activity_task)
        val adapter = PagerAdapter(this)
        val viewPager = findViewById<ViewPager2>(R.id.taskviewPager)
        viewPager.adapter = adapter
    }
}