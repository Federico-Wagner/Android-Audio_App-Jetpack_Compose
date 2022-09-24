package com.example.soundsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.soundsapp.data.DataSource
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.SoundApp
import com.example.soundsapp.ui.theme.SoundsAppTheme

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
//    lateinit var dataBaseRows : List<Audio>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate Called")
        super.onCreate(savedInstanceState)

        DataBase.createDB(applicationContext)
        DataBase.showRecords()

        val dataBaseRows : List<Audio> = DataBase.getAllRecords()
        setContent {
            SoundsAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    SoundApp(DataSource.sounds, dataBaseRows, addAudioBTN, applicationContext)
                }
            }
        }
    }

    private val addAudioBTN = fun() {
        val intent = Intent(this, AddAudioActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }
    override fun onResume() {
        super.onResume()
//        this.dataBaseRows = DataBase.getAllRecords()
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
