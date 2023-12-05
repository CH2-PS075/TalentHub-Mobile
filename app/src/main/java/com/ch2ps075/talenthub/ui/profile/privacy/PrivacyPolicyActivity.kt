package com.ch2ps075.talenthub.ui.profile.privacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch2ps075.talenthub.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}