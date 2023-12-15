package com.ch2ps075.talenthub.ui.profile.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch2ps075.talenthub.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}