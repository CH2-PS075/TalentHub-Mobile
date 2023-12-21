package com.ch2ps075.talenthub.data.repo

import androidx.lifecycle.LiveData
import com.ch2ps075.talenthub.data.local.database.dao.TalentDao
import com.ch2ps075.talenthub.data.local.database.entity.TalentEntity

class FavoriteTalentRepository private constructor(
    private val talentDao: TalentDao,
) {
    suspend fun insertFavoriteTalent(talentEntity: TalentEntity) {
        return talentDao.insertFavoriteTalent(talentEntity)
    }

    suspend fun deleteFavoriteTalent(talentEntity: TalentEntity) {
        return talentDao.deleteFavoriteTalent(talentEntity)
    }

    fun getAllFavoriteTalents(): LiveData<List<TalentEntity>> {
        return talentDao.getAllFavoriteTalents()
    }

    fun getFavoriteTalentByName(talentId: Int): LiveData<TalentEntity> {
        return talentDao.getFavoriteTalentByName(talentId)
    }

    companion object {
        @Volatile
        private var instance: FavoriteTalentRepository? = null
        fun getInstance(
            talentDao: TalentDao,
        ): FavoriteTalentRepository = instance ?: synchronized(this) {
            instance ?: FavoriteTalentRepository(talentDao)
        }.also { instance = it }
    }
}