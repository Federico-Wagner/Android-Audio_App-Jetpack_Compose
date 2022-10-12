package com.example.soundsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.soundsapp.db.DAO.AudioDAO
import com.example.soundsapp.db.DAO.GroupDAO
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.db.entity.Group


@Database(entities = [Audio::class, Group::class], version = 22)
abstract class AudiosDataBase : RoomDatabase() {
    abstract fun audioDAO(): AudioDAO
    abstract fun groupDAO(): GroupDAO
}
