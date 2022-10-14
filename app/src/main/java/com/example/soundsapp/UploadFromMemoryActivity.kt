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
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.SelectAudio
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
import com.example.soundsapp.ui.theme.SoundsAppTheme


class UploadFromMemoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val audioUri: Uri? = intent.clipData?.getItemAt(0)?.uri

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
        addNewAudioScreenObjectStatus.selectedAudioUri = audioUri
        addNewAudioScreenObjectStatus.selectedAudioPath = "audio path new intent - TEST"





        setContent {
            // If inten was launched with "Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION" this line will take that flag -- (not working)
            //LocalContext.current.contentResolver.takePersistableUriPermission(audioUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // thows:  No persistable permission grants found for UID 10445 and Uri content://media/external/audio/media/64325 [user 0]

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
                                DataBase.saveAudioinDB(context)
                                addNewAudioScreenObjectStatus.reset()
                                MediaPlayerFW.reset()
                                println("saving audio!!!")
                                println(audioUri.toString())

                                val text = "Audio saved"
                                val duration = Toast.LENGTH_SHORT
                                val toast = Toast.makeText(applicationContext, text, duration)
                                toast.show()
                                finish()
                            }
                        },
                        context)
                }
            }
        }

    }
}