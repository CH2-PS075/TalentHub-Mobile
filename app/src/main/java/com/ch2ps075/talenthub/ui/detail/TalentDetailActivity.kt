package com.ch2ps075.talenthub.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.local.database.entity.TalentEntity
import com.ch2ps075.talenthub.data.network.api.response.Talent
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivityTalentDetailBinding
import com.ch2ps075.talenthub.helper.loadImage
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.favorite.FavoriteViewModel
import com.ch2ps075.talenthub.ui.search.SearchFragment.Companion.TALENT_ID

class TalentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalentDetailBinding
    private val viewModel by viewModels<TalentDetailViewModel> {
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

        viewModel.getDetailTalent(talentId.toString()).observe(this) { result ->
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
            tvDetailPrice.text = talent.price.toString()
            tvDetailCategory.text = talent.category
            tvDetailQuantity.text = talent.quantity
            tvDetailAddress.text = talent.address
            tvDetailDescription.text = getString(R.string.welcome_conditions_text)
            tvDetailPortfolio.text = talent.portfolio
            contactTalentButton.setOnClickListener { showToast(talent.contact) }
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