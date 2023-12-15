package com.ch2ps075.talenthub.ui.profile.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivitySettingsBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.auth.login.LoginActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupUI() {
        with(binding) {
            ivBack.setOnClickListener { onSupportNavigateUp() }
            myProfile.setOnClickListener { startActivity(Intent(this@SettingsActivity, MyProfileActivity::class.java))}
            information.setOnClickListener { startActivity(Intent(this@SettingsActivity, InformationActivity::class.java))}
            logout.setOnClickListener { showWarningAlert() }
        }
    }

    private fun showWarningAlert() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(getString(R.string.logout_text))
            .setContentText(getString(R.string.logout_confirm_text))
            .setConfirmButton(getString(R.string.logout_text)) {
                viewModel.logout()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            .setCancelButton(getString(R.string.back_title)) { it.dismissWithAnimation() }
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }
}