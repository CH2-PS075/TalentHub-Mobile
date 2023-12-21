package com.ch2ps075.talenthub.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.network.api.response.Talent
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivityTalentDetailBinding
import com.ch2ps075.talenthub.helper.loadImage
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.search.SearchFragment.Companion.TALENT_ID

class TalentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalentDetailBinding
    private val viewModel by viewModels<TalentDetailViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTalentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
        getDetailTalent()
    }

    private fun getDetailTalent() {
        val talentId = intent.getStringExtra(TALENT_ID).toString()
        viewModel.getDetailTalent(talentId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showTalentDetail(result.data)
                        showLoading(false)
                    }

                    is ResultState.Error -> {

                    }
                }
            }
        }
    }

    private fun showTalentDetail(talent: Talent) {
        with(binding) {
            ivDetailPhoto.loadImage(talent.picture)
            tvDetailName.text = talent.talentName
            tvDetailPrice.text = talent.price.toString()
            tvDetailCategory.text = talent.category
            tvDetailQuantity.text = talent.quantity
            tvDetailAddress.text = talent.address
            tvDetailDescription.text = getString(R.string.welcome_conditions_text)
            tvDetailPortfolio.text = talent.portfolio
            contactTalentButton.setOnClickListener {
                showToast(talent.contact)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}