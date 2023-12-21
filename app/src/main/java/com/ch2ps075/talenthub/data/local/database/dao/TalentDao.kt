package com.ch2ps075.talenthub.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch2ps075.talenthub.data.local.database.entity.TalentEntity

@Dao
interface TalentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteTalent(talentEntity: TalentEntity)

    @Delete
    suspend fun deleteFavoriteTalent(talentEntity: TalentEntity)

    @Query("SELECT * FROM talent ORDER BY talentName ASC")
    fun getAllFavoriteTalents(): LiveData<List<TalentEntity>>

    @Query("SELECT * FROM talent WHERE talentId = :talentId")
    fun getFavoriteTalentByName(talentId: Int): LiveData<TalentEntity>
}