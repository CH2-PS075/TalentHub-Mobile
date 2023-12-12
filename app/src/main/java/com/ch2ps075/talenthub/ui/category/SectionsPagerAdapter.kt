package com.ch2ps075.talenthub.ui.category

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return SectionPagerFragment()
    }

    override fun getItemCount(): Int {
        return 10
    }
}