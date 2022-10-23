package com.example.rttodolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rttodolist.databinding.ActivityMainBinding
import com.example.rttodolist.ui.add.addActivity
import com.example.rttodolist.ui.todo.Task
import com.google.android.material.navigation.NavigationView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.w3c.dom.Text
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val BASE_URL = "http://507215.msk-kvm.ru"
    val JSON: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener { view ->
            startActivity(Intent(this,addActivity::class.java))
        }

        val request = Request.Builder().url(BASE_URL + "/task")
            .addHeader("Authorization",ClientToken.token.toString())
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                if(response.code == 200){
                    val listType = object : TypeToken<ArrayList<Task>>(){}.type
                    val builder = GsonBuilder()
                    val gson = builder.create()
                    val tasks : ArrayList<Task> = gson.fromJson(response.body!!.string(),listType) as ArrayList<Task>
                    for(task in tasks){
                        Log.println(Log.INFO,"Task",tasks.toString())
                        if(task.column_title != null){
                            when(task.column_title){
                                "To do" -> taskLists.todoTask.add(task)
                                "In progress" -> taskLists.inProgressTask.add(task)
                                "Done" -> taskLists.doneTask.add(task)
                            }
                        }

                    }
                }
                else{
                    Log.println(Log.INFO,"Main",response.code.toString())
                }
            }
        })


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_todo, R.id.nav_in_progress, R.id.nav_done
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val header: View = navView.getHeaderView(0)
        header.findViewById<TextView>(R.id.UserName).setText(ClientToken.last_name + " " + ClientToken.first_name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}