package com.ch2ps075.talenthub.ui.detail

import androidx.lifecycle.ViewModel
import com.ch2ps075.talenthub.data.repo.TalentRepository

class TalentDetailViewModel (private val talentRepository: TalentRepository) : ViewModel() {
    fun getDetailTalent(talentId: String) = talentRepository.getDetailTalent(talentId)
}