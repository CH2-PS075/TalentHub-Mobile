package com.ch2ps075.talenthub.ui.profile.terms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch2ps075.talenthub.databinding.ActivityTermsOfServicesBinding

class TermsOfServicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTermsOfServicesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsOfServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}