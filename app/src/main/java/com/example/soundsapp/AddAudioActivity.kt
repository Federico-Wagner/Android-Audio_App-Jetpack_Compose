package com.example.soundsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.AddAudioScreen
import com.example.soundsapp.ui.theme.SoundsAppTheme
import java.util.logging.Level
import java.util.logging.Logger


object addNewAudioScreenObjectStatus{
    var selectedAudioUri : Uri? = null
    var selectedAudioUserName : String = "no name yet"
    var selectedAudioFileName : String = ""

    fun reset(){
        this.selectedAudioUri =  null
        this.selectedAudioUserName = ""
        this.selectedAudioFileName = ""
    }
}

class AddAudioActivity : ComponentActivity(){ //AppCompatActivity() { //TODO search difference
    var logger = Logger.getLogger("AddAudioActivity-Loger")
    val audioSearchBTN = fun() {
        val intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val audioUri: Uri? = result.data?.data?.normalizeScheme()
            // Save Uri of selected AUDIO FILE
            addNewAudioScreenObjectStatus.selectedAudioUri = audioUri

            val cursor: Cursor? =
                audioUri?.let { contentResolver.query(it,null, null, null, null, null) }
            try {
                if (cursor != null && cursor.moveToFirst()) {
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
        saveAudioinDB(applicationContext)
        addNewAudioScreenObjectStatus.reset()
        MediaPlayerFW.reset()
        goBackBTN()
    }
    val goBackBTN = fun() {
        //Back to Home
        val startIntent = Intent(applicationContext, MainActivity::class.java)
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(startIntent)
        MediaPlayerFW.reset()
    }

    fun saveAudioinDB(context: Context) {
        logger.log(Level.INFO,"saveAudioinDB - ##########################################################")
        println(addNewAudioScreenObjectStatus.selectedAudioUserName)
        println(addNewAudioScreenObjectStatus.selectedAudioUri)

        //get audio name from textbox
        //get audio uri
        addNewAudioScreenObjectStatus

        DataBase.insertInDB(
            addNewAudioScreenObjectStatus.selectedAudioUserName,
            addNewAudioScreenObjectStatus.selectedAudioFileName,
            addNewAudioScreenObjectStatus.selectedAudioUri
        )

        //Clean addNewAudioScreenObjectStatus
        addNewAudioScreenObjectStatus.reset()
        MediaPlayerFW.reset()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addNewAudioScreenObjectStatus.reset()
        MediaPlayerFW.reset()
        setContent {
            SoundsAppTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(1f),
                    color = Color.Transparent
                ) {
                    AddAudioScreen(audioSearchBTN,
                        saveBTN,
                        goBackBTN,
                        applicationContext)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNewAudioScreenObjectStatus.reset()
        MediaPlayerFW.reset()
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        addNewAudioScreenObjectStatus
        MediaPlayerFW.reset()
    }
}
