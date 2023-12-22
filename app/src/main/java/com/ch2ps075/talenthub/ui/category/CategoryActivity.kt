package com.ch2ps075.talenthub.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivityCategoryBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.home.HomeFragment.Companion.CATEGORY_ID
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        observeSession()
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

    private fun observeSession() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                showWarningAlert()
            }
        }
    }

    private fun showWarningAlert() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(getString(R.string.inaccessible_title))
            .setContentText(getString(R.string.no_session_text))
            .setConfirmButton(getString(R.string.login_text)) {
                startActivity(Intent(this, WelcomeActivity::class.java))
            }
            .setCancelButton(getString(R.string.back_title)) {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .apply { setCanceledOnTouchOutside(false) }
            .show()
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