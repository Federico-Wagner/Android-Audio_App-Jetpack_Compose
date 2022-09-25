package com.example.soundsapp.db.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.soundsapp.db.entity.Audio


@Dao
abstract class AudioDAO {
    @Query("SELECT * FROM audios")
    abstract fun getAll(): List<Audio>

    @Query("SELECT * FROM audios WHERE id = :id")
    abstract fun findById(id: Int): Audio

    fun insert(entity: Audio): Audio {
        entity.id = getNextId(entity) //id re-assignation for avoid db crash on unique PK constraint
        return entity
    }

    @Insert
    abstract fun getNextId(entity: Audio): Long

    @Delete
    abstract fun deleteByEntity(audio: Audio)

    @Delete
    abstract fun deleteAll(audios: List<Audio>)

}