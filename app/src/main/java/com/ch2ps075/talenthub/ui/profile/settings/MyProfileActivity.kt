package com.ch2ps075.talenthub.ui.profile.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.UserModel
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivityMyProfileBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.main.MainViewModel

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
        observeSession()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun observeSession() {
        viewModel.getSession().observe(this) { user -> personalInformation(user) }
    }

    private fun personalInformation(user: UserModel) {
        with(binding) {
            tvUsername.text = if (user.isLogin) user.username else getString(R.string.username_hint)
            tvContact.text = if (user.isLogin) user.contact else getString(R.string.contact_hint)
            tvAddress.text = if (user.isLogin) user.address else getString(R.string.address_hint)
        }
    }
}