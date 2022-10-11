package com.example.soundsapp.db.DAO

import androidx.room.*
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.db.entity.Group


@Dao
abstract class AudioDAO {
    @Query("SELECT * FROM audios")
    abstract fun getAll(): List<Audio>

    @Query("SELECT * FROM audios " +
            "ORDER BY audios.favorite DESC")
    abstract fun getAllSortedByFavorite(): List<Audio>

    @Query("SELECT * FROM audios WHERE id = :id")
    abstract fun findById(id: Int): Audio

    @Query("SELECT * FROM audios WHERE audios.group_id = :groupId")
    abstract fun getAudiosByGroupIdAndSortedByFavorite(groupId: Int): List<Audio>

    @Query(
        "SELECT * FROM audios " +
                "JOIN groups ON audios.group_id = groups.group_id " +
                " ORDER BY audios.favorite DESC"
    )
    abstract fun loadAudiosWithGroupsAndSortedByFavorite(): Map<Audio, Group>

    fun insert(entity: Audio): Audio {
        entity.id = getNextId(entity) //id re-assignation for avoid db crash on unique PK constraint
        return entity
    }

    //FUTURE FEATURE !!!
    @Query("SELECT * FROM audios WHERE audio_user_name LIKE :search ")
    abstract fun findAudiosByName(search: String): List<Audio>

    @Insert
    abstract fun getNextId(entity: Audio): Long

    @Delete
    abstract fun deleteByEntity(audio: Audio)

    @Delete
    abstract fun deleteAll(audios: List<Audio>)

    @Update
    abstract fun updateByEntity(audio: Audio)
    @Update
    abstract fun updateByEntityList(audioList: List<Audio>)
}