package com.ch2ps075.talenthub.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ch2ps075.talenthub.data.local.database.dao.TalentDao
import com.ch2ps075.talenthub.data.local.database.entity.TalentEntity

@Database(
    entities = [TalentEntity::class],
    version = 2,
    exportSchema = false
)
abstract class TalentDatabase : RoomDatabase() {

    abstract fun talentDao(): TalentDao

    companion object {
        @Volatile
        private var INSTANCE: TalentDatabase? = null
        fun getInstance(context: Context): TalentDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TalentDatabase::class.java, "db_favorite_talent"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }
}