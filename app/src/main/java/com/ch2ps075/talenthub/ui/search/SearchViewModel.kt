package com.ch2ps075.talenthub.ui.search

import androidx.lifecycle.ViewModel
import com.ch2ps075.talenthub.data.repo.TalentRepository

class SearchViewModel(private val talentRepository: TalentRepository) : ViewModel() {
    fun getTalentsByName(talentName: String) = talentRepository.getTalentsByName(talentName)
}