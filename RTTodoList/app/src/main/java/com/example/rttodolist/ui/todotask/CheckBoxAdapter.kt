package com.example.rttodolist.ui.todotask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import com.example.rttodolist.R

class CheckBoxAdapter(context:Context,tasks:ArrayList<taskStatus>) : ArrayAdapter<taskStatus>(context, R.layout.todocheck,tasks){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if(convertView == null)
            return getView(position,LayoutInflater.from(context).inflate(R.layout.todocheck,null),parent)

        val task: taskStatus? = getItem(position)
        val checkBox = convertView.findViewById<CheckBox>(R.id.checkBox)
        checkBox.text = task!!.label
        checkBox.isChecked = task.done.equals("1")
        return checkBox
    }



}