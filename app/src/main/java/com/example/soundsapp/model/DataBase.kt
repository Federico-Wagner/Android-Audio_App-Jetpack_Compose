package com.example.soundsapp.model

import android.content.Context
import android.net.Uri
import androidx.room.Room
import com.example.soundsapp.db.AudiosDataBase
import com.example.soundsapp.db.DAO.AudioDAO
import com.example.soundsapp.db.entity.Audio


object DataBase {
    lateinit var db: AudiosDataBase
    private lateinit var audioDao : AudioDAO

    fun createDB(applicationContext: Context){
        val _db = Room.databaseBuilder(
            applicationContext,
            AudiosDataBase::class.java, "database-name"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        this.db = _db
        this.audioDao = _db.audioDAO()
    }

    fun insertInDB(audioUserName: String,
                   audioFileName: String,
                   audioURI: Uri?) {
        val newAudio = Audio(0,
            audioUserName,
            audioFileName,
            audioURI.toString())
        this.audioDao.insert(newAudio)
    }

    fun showRecords() {
        println("audios:")
        val audios: List<Audio> = this.audioDao.getAll()
        audios.forEach {
            println(
                "---------------------\n" +
                        it.id + " - " +
                        it.audioUserName + " - " +
                        it.audioFileName + "\n" +
                        it.audioURI +
                        "\n----------------------"
            )
        }
    }
    fun getAllRecords(): List<Audio> {
        return this.audioDao.getAll()
    }
}
