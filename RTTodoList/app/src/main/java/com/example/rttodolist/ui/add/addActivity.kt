package com.example.rttodolist.ui.add

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.rttodolist.R

class addActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        supportFragmentManager.beginTransaction().add(R.id.addFragmentContriner,addFragment()).commit()
    }

}