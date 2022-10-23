package com.example.rttodolist.ui.todo

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rttodolist.ui.todotask.MainTaskFragment


class PagerAdapter(fm: FragmentActivity,val fragments: ArrayList<TaskListFragment>) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> fragments.get(position)
            1 -> fragments.get(position)
            2 -> fragments.get(position)
            3 -> fragments.get(position)
            else -> fragments.get(0)
        }
    }

}

