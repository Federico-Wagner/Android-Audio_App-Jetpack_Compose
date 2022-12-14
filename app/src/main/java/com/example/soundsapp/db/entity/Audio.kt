package com.example.soundsapp.db.entity

import androidx.room.*


@Entity(tableName = "audios")
data class Audio(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "audio_user_name") var audioUserName: String,
    @ColumnInfo(name = "audio_file_name") var audioFileName: String,
    @ColumnInfo(name = "audio_file_uri") var audioURI: String,
    @ColumnInfo(name = "audio_file_pathi") var audioPath: String,
    @ColumnInfo(name = "favorite") var favorite: Boolean,
    @ColumnInfo(name = "group_id") var groupId: Long?
)
