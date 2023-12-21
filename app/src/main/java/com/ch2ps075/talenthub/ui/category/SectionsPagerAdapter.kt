package com.ch2ps075.talenthub.ui.category

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ch2ps075.talenthub.ui.category.CategoryActivity.Companion.TAB_CATEGORY_TITLES

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val context: Context = activity

    override fun createFragment(position: Int): Fragment {
        val fragment = SectionPagerFragment()
        fragment.arguments = Bundle().apply {
            putString(SectionPagerFragment.ARG_CATEGORY_NAME, context.getString(TAB_CATEGORY_TITLES[position]))
        }
        return fragment
    }

    override fun getItemCount() = 8
}