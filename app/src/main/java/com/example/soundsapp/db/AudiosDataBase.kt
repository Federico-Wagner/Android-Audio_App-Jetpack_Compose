package com.example.soundsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.soundsapp.db.DAO.AudioDAO
import com.example.soundsapp.db.entity.Audio


@Database(entities = [Audio::class], version = 12)
abstract class AudiosDataBase : RoomDatabase() {
    abstract fun audioDAO(): AudioDAO
}
