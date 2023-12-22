package com.ch2ps075.talenthub.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.local.database.entity.TalentEntity
import com.ch2ps075.talenthub.data.network.api.response.Talent
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivityTalentDetailBinding
import com.ch2ps075.talenthub.helper.loadImage
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.favorite.FavoriteViewModel
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel
import com.ch2ps075.talenthub.ui.search.SearchFragment.Companion.TALENT_ID

class TalentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalentDetailBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }
    private val talentViewModel by viewModels<TalentDetailViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }
    private lateinit var favoriteTalentLiveData: LiveData<TalentEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTalentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeSession()
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
        getDetailTalent()
    }

    private fun getDetailTalent() {
        val talentId = intent.getIntExtra(TALENT_ID, 0)

        favoriteTalentLiveData = favoriteViewModel.getFavoriteTalentByName(talentId)
        favoriteTalentLiveData.observe(this) { favoriteTalent ->
            with(binding) {
                if (favoriteTalent == null) {
                    ivFavorite.setImageDrawable(ContextCompat.getDrawable(ivFavorite.context, R.drawable.ic_favorite))
                } else {
                    ivFavorite.setImageDrawable(ContextCompat.getDrawable(ivFavorite.context, R.drawable.ic_favorite_active))
                }
            }
        }

        talentViewModel.getDetailTalent(talentId.toString()).observe(this) { result ->
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
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showTalentDetail(talent: Talent) {
        with(binding) {
            ivDetailPhoto.loadImage(talent.picture)
            tvDetailName.text = talent.talentName
            tvDetailPrice.text = getString(R.string.price_talent_detail, talent.price.toString())
            tvDetailCategory.text = talent.category
            tvDetailQuantity.text = talent.quantity
            tvDetailAddress.text = talent.address
            tvDetailDescription.text = talent.description
            portfolioTalentButton.setOnClickListener { openPortfolioOnBrowser(talent.portfolio) }
            contactTalentButton.setOnClickListener { openWhatsAppChat(talent.contact) }
            ivFavorite.setOnClickListener {
                val favoriteTalent = favoriteTalentLiveData.value

                val talentEntity = TalentEntity(
                    talentId = talent.talentId,
                    talentName = talent.talentName,
                    quantity = talent.quantity,
                    address = talent.address,
                    category = talent.category,
                    contact = talent.contact,
                    price = talent.price,
                    picture = talent.picture,
                    portfolio = talent.portfolio,
                    description = talent.description,
                    latitude = talent.latitude,
                    longitude = talent.longitude
                )

                if (favoriteTalent == null) {
                    favoriteViewModel.insertFavoriteTalent(talentEntity)
                } else {
                    favoriteViewModel.deleteFavoriteTalent(talentEntity)
                }
            }
        }
    }

    private fun openPortfolioOnBrowser(urlPortfolio: String) {
        if (isValidUrl(urlPortfolio)) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlPortfolio))
            startActivity(intent)
        } else {
            runOnUiThread {
                Toast.makeText(this, getString(R.string.url_invalid), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidUrl(url: String): Boolean {
        return try {
            val uri = Uri.parse(url)
            uri.scheme == "https" && uri.host != null
        } catch (e: Exception) {
            false
        }
    }

    private fun openWhatsAppChat(contactNumber: String) {
        val message = "Saya tertarik untuk bisa berkolaborasi dengan kamu, saya menemukanmu dari aplikasi TalentHub"
        val url = "https://wa.me/$contactNumber?text=${Uri.encode(message)}"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}