package com.example.soundsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.soundsapp.db.DAO.AudioDAO
import com.example.soundsapp.db.entity.Audio

//import com.example.soundsapp.db.DAO.AudioDAO


@Database(entities = [Audio::class], version = 5)
abstract class AudiosDataBase : RoomDatabase() {
    abstract fun audioDAO(): AudioDAO
}


