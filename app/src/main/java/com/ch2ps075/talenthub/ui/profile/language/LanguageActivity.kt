package com.ch2ps075.talenthub.ui.profile.language

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivityLanguageBinding
import com.ch2ps075.talenthub.helper.LanguageUtil
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.main.MainActivity

class LanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageBinding

    private val languageViewModel by viewModels<LanguageViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        languageViewModel.getLanguageSetting().observe(this) { languageCode ->
            updateLanguageViews(languageCode)
        }

        setupLanguageSelection()
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
    }

    private fun updateLanguageViews(languageCode: String) {
        LanguageUtil.setLocale(this, languageCode)
        binding.ivSelectedIndonesia.visibility = if (languageCode == "in") View.VISIBLE else View.GONE
        binding.ivSelectedEnglish.visibility = if (languageCode == "en") View.VISIBLE else View.GONE
    }

    private fun setupLanguageSelection() {
        binding.englishLanguage.setOnClickListener {
            changeLanguage("en", "Language is changed to English")
        }

        binding.indonesiaLanguage.setOnClickListener {
            changeLanguage("in", "Bahasa diubah ke Bahasa Indonesia")
        }
    }

    private fun changeLanguage(languageCode: String, toastMessage: String) {
        LanguageUtil.setLocale(this, languageCode)
        languageViewModel.saveLanguageSetting(languageCode)
        showToast(toastMessage)
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}