package com.ch2ps075.talenthub.ui.home

import androidx.lifecycle.ViewModel
import com.ch2ps075.talenthub.data.repo.TalentRepository

class HomeViewModel(private val talentRepository: TalentRepository) : ViewModel() {
    fun getTalents() = talentRepository.getTalents()

    fun getTalentsByCategory(categoryName: String) = talentRepository.getTalentsByCategory(categoryName)
}