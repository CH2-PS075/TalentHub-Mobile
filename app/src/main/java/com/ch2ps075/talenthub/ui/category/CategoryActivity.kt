package com.ch2ps075.talenthub.ui.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.ActivityCategoryBinding
import com.ch2ps075.talenthub.ui.home.HomeFragment.Companion.CATEGORY_ID
import com.google.android.material.tabs.TabLayoutMediator

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setupUI()
        setContentView(binding.root)
    }

    private fun setupUI() {
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPagerCategory.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabCategory, binding.viewPagerCategory) { tab, position ->
            tab.text = resources.getString(TAB_CATEGORY_TITLES[position])
        }.attach()

        val categoryId = intent.getIntExtra(CATEGORY_ID, 0)
        binding.tabCategory.getTabAt(categoryId)?.select()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        @StringRes
        val TAB_CATEGORY_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3,
            R.string.tab_text_4,
            R.string.tab_text_5,
            R.string.tab_text_6,
            R.string.tab_text_7,
            R.string.tab_text_8
        )
    }
}