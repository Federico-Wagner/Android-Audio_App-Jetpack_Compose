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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.soundsapp.db.DAO.GroupDAO
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.db.entity.Group
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
import com.example.soundsapp.ui.theme.SoundsAppTheme


class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Called")

        DataBase.createDB(applicationContext)

        if(DataBase.groupGetAll().size == 0){
            DataBase.groupCreate("General")
        }

        setContent {
            SoundsAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    AudioAppScreen(audioSearchBTN, applicationContext)
                }
            }
        }
    }
}
