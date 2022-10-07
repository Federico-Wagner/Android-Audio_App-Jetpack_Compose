package com.example.soundsapp

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.SoundApp
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
import com.example.soundsapp.ui.theme.SoundsAppTheme


class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    lateinit var dataBaseRows : List<Audio>

    val audioSearchBTN = fun() {
        val intent = Intent()
        intent.type = "audio/*"
//        intent.action = Intent.ACTION_GET_CONTENT     //TEMPORAL PERMISSION
        intent.action = Intent.ACTION_OPEN_DOCUMENT     //PERMANENT PERMISSION
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        resultLauncher.launch(intent)
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val audioUri: Uri? = result.data?.data?.normalizeScheme()
            // Save Uri & PATH of selected AUDIO FILE
            addNewAudioScreenObjectStatus.selectedAudioUri = audioUri
            addNewAudioScreenObjectStatus.selectedAudioPath = result.data?.data?.path

            //Retrieving audio fileName
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
            )
            val cursor: Cursor? =
                contentResolver.query(audioUri!!, projection, null, null, null, null)
            try {
                if ((cursor != null) && cursor.moveToFirst()) {
                    val nameIndex: Int = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (!cursor.isNull(nameIndex)) {
                        addNewAudioScreenObjectStatus.selectedAudioFileName = cursor.getString(nameIndex)
                    }
                }
            } finally {
                cursor?.close()
            }
        }
    }

    val saveBTN = fun(audioName : String) {
        DataBase.saveAudioinDB(applicationContext)
        addNewAudioScreenObjectStatus.reset()
        MediaPlayerFW.reset()
        goBackBTN()
    }
    val goBackBTN = fun() {  //TODO FIX NAVIGATION
        //Back to Home
        val startIntent = Intent(applicationContext, MainActivity::class.java)
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(startIntent)
        MediaPlayerFW.reset()
    }

    private val addAudioBTN = fun() {
//        val intent = Intent(this, AddAudioActivity::class.java)
//        startActivity(intent)
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
//                    SoundApp(this.dataBaseRows, addAudioBTN,audioSearchBTN,saveBTN,goBackBTN, applicationContext)
                    AudioApp(this.dataBaseRows, addAudioBTN,audioSearchBTN,saveBTN,goBackBTN, applicationContext)
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
