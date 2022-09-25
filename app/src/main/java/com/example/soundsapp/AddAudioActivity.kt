package com.example.soundsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.AddAudioScreen
import com.example.soundsapp.ui.PlayerControls
import com.example.soundsapp.ui.theme.Green200
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
//        return addNewAudioScreenObjectStatus.selectedAudioUserName
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val audioUri: Uri? = result.data?.data?.normalizeScheme()
            // Save Uri of selected AUDIO FILE
            addNewAudioScreenObjectStatus.selectedAudioUri = audioUri

            // TEST START
//            MediaPlayerFW.setAndPlay(applicationContext, Audio(566,"test","test.mpX", audioUri.toString() ))
            // TEST END

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


        //generate Audio Entity object

        //persist Object in DB





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



//
//@Composable
//fun AddAudioScreen( audioSearchBTN: () -> Unit,
//                    saveBTN: (String) -> Unit,
//                    goBackBTN: () -> Unit,
//                    context: Context,
//                    modifier: Modifier = Modifier)
//{
//    var audioName by remember { mutableStateOf(addNewAudioScreenObjectStatus.selectedAudioUserName) }
//    var audioFile by remember { mutableStateOf(addNewAudioScreenObjectStatus.selectedAudioFileName) }
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(10.dp),
//        verticalArrangement = Arrangement.SpaceEvenly,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        // AUDIO NAME FROM USER
//        OutlinedTextField(
//            modifier = modifier
//                .fillMaxWidth(),
//            value = audioName,
//            onValueChange = {
//                audioName = it
//                addNewAudioScreenObjectStatus.selectedAudioUserName = it
//                },
//            label = { Text("Enter audio name: ") },
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
//        )
//        Row(
//            modifier = modifier
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//        ){
//            // AUDIO FILE NAME FROM URI
//            OutlinedTextField(
//                modifier = modifier,
//                value =  audioFile,
//                onValueChange = {
//                    audioFile = it
//                    addNewAudioScreenObjectStatus.selectedAudioFileName = it
//                    },
//                label = { Text("Selected file: ") },
//                readOnly = true
//            )
//            //SEARCH AUDIO BUTTON
//            Box(modifier = modifier
//                .clip(RoundedCornerShape(30))
//                .padding(start = 26.dp, top = 5.dp),
//                contentAlignment = Alignment.Center
//            ){
//                Icon(Icons.Rounded.Add,
//                    contentDescription = "Add Audio",
//                    modifier = modifier
//                        .clickable {
//                            audioSearchBTN()
//                            audioFile = addNewAudioScreenObjectStatus.selectedAudioFileName
//                        }
//                        .size(40.dp)
//                        .border(width = 3.dp, color = Green200, shape = CircleShape)
//                )
//            }
//        }
//
//        PlayerControls(context, modifier = modifier)
//
//        Row(modifier = modifier
//            .fillMaxWidth()
//            ,horizontalArrangement = Arrangement.SpaceEvenly
//            ){
//            Button(
//                modifier = modifier.padding(top = 15.dp),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
//                onClick = { goBackBTN() }) {
//                Text(text = "Discard")
//            }
//            Button(
//                modifier = modifier.padding(top = 15.dp),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
//                onClick = { saveBTN(audioName) }) {
//                Text(text = "Save")
//            }
//        }
//    }
//}

//@Composable
//fun PlayerControls(context: Context,
//                   modifier: Modifier = Modifier
//    ){
//    var state : MediaPlayerFW.PlayerState = MediaPlayerFW.state
//
//    Box(modifier = modifier
//        .size(55.dp)
//        .clip(RoundedCornerShape(50)),
//    ){
//        Icon(Icons.Rounded.PlayArrow, contentDescription = "Play",
//            modifier = modifier
//                .clickable {
////                    MediaPlayerFW.tap(context, addNewAudioScreenObjectStatus.selectedAudioUri)
//                    MediaPlayerFW.tap(context, Audio(0,addNewAudioScreenObjectStatus.selectedAudioUserName
//                    ,addNewAudioScreenObjectStatus.selectedAudioFileName,
//                    addNewAudioScreenObjectStatus.selectedAudioUri.toString()))
//                    state = MediaPlayerFW.state
//                }
//                .size(55.dp)
//                .clip(RoundedCornerShape(50)),
//            tint = Green200
//        )
//    }
//}

//Icons.Rounded.PlayArrow
//Icons.Rounded.Pause
//Icons.Rounded.Stop