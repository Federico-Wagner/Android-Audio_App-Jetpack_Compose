package com.example.soundsapp

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.soundsapp.helpers.FileManger
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
import com.example.soundsapp.ui.theme.SoundsAppTheme


class MainActivity : ComponentActivity() {

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
            // Save Uri , PATH & FILE NAME of selected AUDIO FILE
            addNewAudioScreenObjectStatus.selectedAudioUri = audioUri
            addNewAudioScreenObjectStatus.selectedAudioPath = result.data?.data?.path
            addNewAudioScreenObjectStatus.selectedAudioFileName = FileManger.getFileName(audioUri,contentResolver)!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WARMUP
        DataBase.createDB(applicationContext)
        if(DataBase.getAllGroups().size == 0){
            DataBase.groupCreate("General", false)
        }
        //DEBUG - DEVELOP
        DataBase.showAllAudioRecords()
        DataBase.showAllGroupsRecords()
//        DataBase.deleteAllRecords()


        setContent {
            SoundsAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AudioAppScreen(audioSearchBTN, applicationContext, contentResolver)
                }
            }
        }
    }
}
