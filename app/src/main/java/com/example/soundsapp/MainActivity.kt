package com.example.soundsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.SoundApp
import com.example.soundsapp.ui.theme.SoundsAppTheme


class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    lateinit var dataBaseRows : List<Audio>

    private val addAudioBTN = fun() {
        val intent = Intent(this, AddAudioActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Called")

        DataBase.createDB(applicationContext)
        DataBase.showRecords()
        this.dataBaseRows = DataBase.getAllRecords()

        MediaPlayerFW.reset()

        setContent {
            SoundsAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    SoundApp(this.dataBaseRows, addAudioBTN, applicationContext, contentResolver)
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
        this.dataBaseRows = DataBase.getAllRecords()
        Log.d(TAG, "onResume Called")
    }
    override fun onRestart() {
        super.onRestart()
        this.dataBaseRows = DataBase.getAllRecords()
        Log.d(TAG, "onRestart Called")
    }
    override fun onPause() {
        super.onPause()
        this.dataBaseRows = DataBase.getAllRecords()
        Log.d(TAG, "onPause Called")
    }
    override fun onStop() {
        super.onStop()
        this.dataBaseRows = DataBase.getAllRecords()
        Log.d(TAG, "onStop Called")
    }
    override fun onDestroy() {
        super.onDestroy()
        this.dataBaseRows = DataBase.getAllRecords()
        Log.d(TAG, "onDestroy Called")
    }
}
