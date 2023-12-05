package com.ch2ps075.talenthub.ui.profile.language

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch2ps075.talenthub.databinding.ActivityLanguageBinding

class LanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}