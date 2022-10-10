package com.example.soundsapp.db.DAO

import androidx.room.*
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.db.entity.Group


@Dao
abstract class AudioDAO {
    @Query("SELECT * FROM audios")
    abstract fun getAll(): List<Audio>

    @Query("SELECT * FROM audios WHERE id = :id")
    abstract fun findById(id: Int): Audio


    @Query(
        "SELECT * FROM audios " +
                "JOIN groups ON audios.group_id = groups.id"
    )
    abstract fun loadAudiosWithGroups(): Map<Audio, List<Group>>



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

    @Update
    abstract fun updateByEntity(audio: Audio)

}