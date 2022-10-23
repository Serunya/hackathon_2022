package com.example.rttodolist.ui.todotask

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rttodolist.ui.chat.ChatFragment


class PagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MainTaskFragment()
            1 -> ChatFragment()
            else -> MainTaskFragment()
        }
    }

}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)
