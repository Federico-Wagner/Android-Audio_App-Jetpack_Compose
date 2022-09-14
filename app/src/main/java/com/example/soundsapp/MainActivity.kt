package com.example.soundsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.soundsapp.data.DataSource
import com.example.soundsapp.helpers.getPlayer
import com.example.soundsapp.ui.theme.SoundsAppTheme
import com.example.soundsapp.ui.SoundApp

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate Called")
        super.onCreate(savedInstanceState)
        setContent {
            SoundsAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    SoundApp(DataSource.sounds, getPlayer(LocalContext.current))
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
