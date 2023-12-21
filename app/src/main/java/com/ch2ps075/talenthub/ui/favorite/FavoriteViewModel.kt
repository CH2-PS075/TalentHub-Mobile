package com.ch2ps075.talenthub.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps075.talenthub.data.local.database.entity.TalentEntity
import com.ch2ps075.talenthub.data.repo.FavoriteTalentRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteTalentRepository: FavoriteTalentRepository) : ViewModel() {
    fun getAllFavoriteTalents() = favoriteTalentRepository.getAllFavoriteTalents()

    fun getFavoriteTalentByName(talentId: Int) = favoriteTalentRepository.getFavoriteTalentByName(talentId)

    fun insertFavoriteTalent(talentEntity: TalentEntity) {
        viewModelScope.launch {
            favoriteTalentRepository.insertFavoriteTalent(talentEntity)
        }
    }

    fun deleteFavoriteTalent(talentEntity: TalentEntity) {
        viewModelScope.launch {
            favoriteTalentRepository.deleteFavoriteTalent(talentEntity)
        }
    }
}