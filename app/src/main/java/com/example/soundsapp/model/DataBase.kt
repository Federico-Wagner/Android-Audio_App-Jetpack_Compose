package com.example.soundsapp.model

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.soundsapp.db.AudiosDataBase
import com.example.soundsapp.db.DAO.AudioDAO
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.AudioPlayer

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

    fun insertInDB(intent: Intent, name: String, context: Context) {
        println("intent: ")
        println(intent)

        val player = MediaPlayer.create(context, Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)))
        player.start()

        val uri = intent.toUri(Intent.URI_INTENT_SCHEME)
        println("uri: ")
        println(uri)
        val newAudio: Audio = Audio(0, uri, name)
        this.audioDao.insert(newAudio)
    }

    fun showRecords() {
        println("audios:")
        val audios: List<Audio> = this.audioDao.getAll()
        audios.forEach {
            println(
                "----------\n" +
                        it.id + " - " + it.audioURI + " - " + it.audioFile +
                        "\n----------"
            )
        }
    }
    fun getAllRecords(): List<Audio> {
        return this.audioDao.getAll()
    }
}
