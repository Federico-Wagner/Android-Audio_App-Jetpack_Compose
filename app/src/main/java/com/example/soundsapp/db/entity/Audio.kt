package com.example.soundsapp.db.entity

import android.net.Uri
import androidx.room.*


@Entity(tableName = "audios")
data class Audio(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "audio_name") var audioURI: String,
    @ColumnInfo(name = "audio_file") var audioFile: String
)
//) {
////    @Dao
////    abstract class AudioDAO {
////        @Query("SELECT * FROM audios")
////        abstract fun getAll(): List<Audio>
////
////        @Query("SELECT * FROM audios WHERE id = :id")
////        abstract fun findById(id: Int): Audio
////
////        fun insert(entity: Audio): Audio {
////            entity.id = getNextId(entity) //id re-assignation for avoid db crash on unique PK constraint
////            return entity
////        }
////
////        @Insert
////        abstract fun getNextId(entity: Audio): Long
////
////        @Delete
////        abstract fun deleteByEntity(audio: Audio)
////    }
//}