package com.example.soundsapp

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContentResolverCompat
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.SoundApp
import com.example.soundsapp.ui.theme.SoundsAppTheme
import java.util.logging.Logger


class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    lateinit var dataBaseRows : List<Audio>


    val TESTURI = fun(audio: Audio){}

    private val addAudioBTN = fun() {
        val intent = Intent(this, AddAudioActivity::class.java)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MediaPlayerFW.reset()
        Log.d(TAG, "onCreate Called")
//        contentResolver.persistedUriPermissions

        DataBase.createDB(applicationContext)
        DataBase.showRecords()

        this.dataBaseRows = DataBase.getAllRecords()

        setContent {
            SoundsAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    SoundApp(this.dataBaseRows, addAudioBTN, applicationContext, contentResolver, TESTURI)
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
