package com.example.soundsapp

import android.content.*
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.soundsapp.helpers.FileManger
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.helpers.ToastHelper
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.SelectAudio
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
import com.example.soundsapp.ui.theme.SoundsAppTheme


class UploadFromMemoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WARMUP
        DataBase.createDB(applicationContext)
        if(DataBase.getAllGroups().size == 0){
            DataBase.groupCreate("General", false)
        }

        val originalAudioUri: Uri? = intent.clipData?.getItemAt(0)?.uri
        addNewAudioScreenObjectStatus.selectedAudioUri = originalAudioUri
        addNewAudioScreenObjectStatus.selectedAudioFileName = FileManger.getFileName(originalAudioUri,contentResolver)!!


        setContent {
            UploadFromMemoryActivityScreen(finish = {finish()}, contentResolver)
        }
    }
}

@Composable
fun UploadFromMemoryActivityScreen(
    finish : ()-> Unit,
    r: ContentResolver
){
    val audioSavedMsg = stringResource(R.string.audioSaved)
    val audioErrorMsg = stringResource(R.string.audioError)
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
                        if(FileManger.onSave(  context,
                                addNewAudioScreenObjectStatus.selectedAudioUri!! ,
                                addNewAudioScreenObjectStatus.selectedAudioFileName,
                                r)) {
                                    ToastHelper.sendToastMesage( audioSavedMsg, context)
                        }else{
                                    ToastHelper.sendToastMesage( audioErrorMsg, context)
                        }
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
