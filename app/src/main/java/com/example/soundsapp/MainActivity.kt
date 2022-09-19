package com.example.soundsapp

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import com.example.soundsapp.data.DataSource
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.db.AudiosDataBase
import com.example.soundsapp.helpers.AudioPlayer
import com.example.soundsapp.ui.theme.SoundsAppTheme
import com.example.soundsapp.ui.SoundApp

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate Called")
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AudiosDataBase::class.java, "database-name"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        val audioDao = db.audioDAO()
        val newAudio : Audio = Audio( 0, "name4", "file1.mp3")
        audioDao.insert(newAudio)

        val audios: List<Audio> = audioDao.getAll()
        println("audios:")
        audios.forEach{
            println("----------\n" +
                    it.id + " - " + it.audioName + " - " + it.audioFile +
                    "\n----------"
            )
        }


        setContent {
            SoundsAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    SoundApp(DataSource.sounds, this@MainActivity)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }
}
