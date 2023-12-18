package com.ch2ps075.talenthub.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.ActivityTalentRecommendationsBinding

class TalentRecommendationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalentRecommendationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTalentRecommendationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
        setupAutoCompleteOptions(binding.autoCompleteCategory, R.array.category_titles)
        setupAutoCompleteOptions(binding.autoCompleteGrouptype, R.array.group_type_titles)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupAutoCompleteOptions(
        autoCompleteTextView: AutoCompleteTextView,
        arrayResId: Int,
    ) {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(arrayResId)
        )
        with(autoCompleteTextView) {
            setAdapter(adapter)
            setOnItemClickListener { _, _, position, _ ->
                val selectedCategory = adapter.getItem(position).toString()
                Toast.makeText(this@TalentRecommendationsActivity, selectedCategory, Toast.LENGTH_SHORT).show()
            }
        }
    }
}