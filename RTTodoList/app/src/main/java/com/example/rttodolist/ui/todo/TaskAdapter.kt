package com.example.rttodolist.ui.todo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.rttodolist.R
import com.example.rttodolist.ui.todotask.TaskActivity

class TaskAdapter(context: Context,tasks : ArrayList<Task>) : ArrayAdapter<Task>(context, R.layout.task_view,tasks) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if(convertView == null){
            return getView(position,LayoutInflater.from(context).inflate(R.layout.task_view,null),parent)
        }
        val task = getItem(position)
        convertView.findViewById<TextView>(R.id.taskViewTitle).text = task!!.title
        convertView.findViewById<TextView>(R.id.taskViewDescription).text = task.description
        convertView.findViewById<TextView>(R.id.taskViewDate).text = task.deadline
        convertView.findViewById<TextView>(R.id.taskViewExecutor).text = task.executor_id
        convertView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                taskId.task = task
                context.startActivity(Intent(context,TaskActivity::class.java))
            }
        })
        return convertView
    }
}