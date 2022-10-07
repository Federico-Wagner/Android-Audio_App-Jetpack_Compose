package com.example.soundsapp.model

import android.content.Context
import android.net.Uri
import androidx.room.Room
import com.example.soundsapp.db.AudiosDataBase
import com.example.soundsapp.db.DAO.AudioDAO
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus


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
                   audioURI: Uri?,
                    audioPath: String?) {
        val newAudio = Audio(0,
            audioUserName,
            audioFileName,
            audioURI.toString(),
            audioPath.toString())
        this.audioDao.insert(newAudio)
    }

    fun updateAudioInDB(audio: Audio, context: Context) {
        //update in DB
        this.audioDao.updateByEntity(audio)
    }

    fun deleteAudio(audio: Audio, context: Context) {
        //Remove audio from DB
        this.audioDao.deleteByEntity(audio)
        //TODO Revoke app file access
    }

    fun saveAudioinDB(context: Context) {
        //persist in DB
        DataBase.insertInDB(
            addNewAudioScreenObjectStatus.selectedAudioUserName,
            addNewAudioScreenObjectStatus.selectedAudioFileName,
            addNewAudioScreenObjectStatus.selectedAudioUri,
            addNewAudioScreenObjectStatus.selectedAudioPath
        )
        //Clean addNewAudioScreenObjectStatus
        addNewAudioScreenObjectStatus.reset()
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
                        it.audioURI + "\n" +
                        it.audioPath +
                        "\n----------------------"
            )
        }
    }
    fun getAllRecords(): List<Audio> {
        return this.audioDao.getAll()
    }
    fun deleteAllRecords() {
        this.audioDao.deleteAll(this.getAllRecords())
    }
}
