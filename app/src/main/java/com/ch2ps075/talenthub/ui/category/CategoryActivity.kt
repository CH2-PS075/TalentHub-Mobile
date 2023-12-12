package com.ch2ps075.talenthub.ui.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.ActivityCategoryBinding
import com.google.android.material.tabs.TabLayoutMediator

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    private val adapter = SectionsPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }

        binding.viewPagerCategory.adapter = adapter

        TabLayoutMediator(binding.tabCategory, binding.viewPagerCategory) { tab, _ ->
            tab.text = resources.getString(R.string.login_text)
        }.attach()

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}