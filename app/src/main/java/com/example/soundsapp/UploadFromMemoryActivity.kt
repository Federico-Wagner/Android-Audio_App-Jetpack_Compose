package com.example.soundsapp

import android.content.*
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.UnusedAppRestrictionsConstants
import androidx.core.net.toUri
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.FileManger
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.helpers.ToastHelper
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.SelectAudio
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
import com.example.soundsapp.ui.theme.SoundsAppTheme
import java.io.*
import java.time.Instant
import java.util.*


class UploadFromMemoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val originalAudioUri: Uri? = intent.clipData?.getItemAt(0)?.uri

        //Retrieving audio fileName
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
        )
        val cursor: Cursor? =
            contentResolver.query(originalAudioUri!!, projection, null, null, null, null)
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
        addNewAudioScreenObjectStatus.selectedAudioUri = originalAudioUri


        setContent {
            val context = LocalContext.current
            SoundsAppTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    SelectAudio(
                        DataBase.getAllGroups(),
                        navigateToGroupManagerScreen = {
//                            MediaPlayerFW.reset()
//                            navController.navigate(AppScreen.GroupManager.name)
                        },
                        audioSearchBTN = {},
                        discardBTN = {
//                            addNewAudioScreenObjectStatus.reset()
//                            MediaPlayerFW.reset()
//                            navController.navigate(AppScreen.Start.name)
                            finish()
                        },
                        saveBTN = {
                            if(addNewAudioScreenObjectStatus.isSavable()) {
                                //Copy file and Save Uri
                                FileManger.onSave(  context,
                                                    addNewAudioScreenObjectStatus.selectedAudioUri!! ,
                                                    addNewAudioScreenObjectStatus.selectedAudioFileName,
                                                    contentResolver)
                                //Reset MediaPlayerFW
                                MediaPlayerFW.reset()
                                //End Activity
                                finish()
                            }
                        },
                        context)
                }
            }
        }

    }
}