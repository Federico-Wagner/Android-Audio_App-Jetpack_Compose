package com.example.soundsapp.ui

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.theme.Black900
import com.example.soundsapp.ui.theme.Green200
import com.example.soundsapp.ui.theme.Green500
import com.example.soundsapp.ui.theme.Red300


//object editAudioScreenObjectStatus{
//    var selectedAudioUri: Uri? = null
//    var selectedAudioPath: String? = null
//    var selectedAudioUserName: String = ""
//    var selectedAudioFileName: String = ""
//    var playerState : MediaPlayerFW.PlayerState = MediaPlayerFW.state
//
//    fun reset(){
//        this.selectedAudioUri =  null
//        this.selectedAudioPath = null
//        this.selectedAudioUserName = ""
//        this.selectedAudioFileName = ""
//        this.playerState = MediaPlayerFW.PlayerState.STOP
//    }
//    fun isSavable(): Boolean {
//        return( this.selectedAudioUri != null &&
//                this.selectedAudioUserName != "" &&
//                this.selectedAudioPath != null &&
//                this.selectedAudioFileName != "" )
//    }
//}

object editAudioObjectStatus{
    var selectedAudio : Audio? = null
    fun reset() { this.selectedAudio = null }
}

@Composable
fun EditAudio(  audio : Audio,
                discardBTN: () -> Unit,
                saveBTN: (Audio) -> Unit,
                deleteBTN: (Audio) -> Unit,
                context: Context,
                modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .height(360.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .background(Black900, RoundedCornerShape(10.dp))
            .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
            .clickable(enabled = false) {  }

    ) {
        EditAudioLayOut(
            audio,
            discardBTN,
            saveBTN,
            deleteBTN,
            context)
    }
}

@Composable
fun EditAudioLayOut(audio : Audio,
                    discardBTN: () -> Unit,
                    saveBTN: (Audio) -> Unit,
                    deleteBTN: (Audio) -> Unit,
                    context: Context,
                    modifier: Modifier = Modifier
)
{
    var audioName by remember { mutableStateOf(audio.audioUserName) }
//    var audioFile by remember { mutableStateOf(addNewAudioScreenObjectStatus.selectedAudioFileName) }
    var playerState by remember { mutableStateOf(addNewAudioScreenObjectStatus.playerState) }

    val onFinish = fun(){
        playerState = MediaPlayerFW.state
    }
    val update = fun(){
        playerState = MediaPlayerFW.state
    }
    val onTap = fun(){
        MediaPlayerFW.tap(
            context, Audio(
                0, addNewAudioScreenObjectStatus.selectedAudioUserName,
                addNewAudioScreenObjectStatus.selectedAudioFileName,
                addNewAudioScreenObjectStatus.selectedAudioUri.toString(),
                addNewAudioScreenObjectStatus.selectedAudioPath.toString()
            ),
            onFinish)
        update()
    }

    val saveBtnColor = when ( addNewAudioScreenObjectStatus.isSavable() ) {
        true -> { Green200 }
        false -> { Green500 }
    }

    Column(
        modifier = modifier
            .height(400.dp)
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // AUDIO NAME FROM USER
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = audioName,
            onValueChange = {
                audioName = it
                audio.audioUserName = it
            },
            label = { Text("Audio name: ") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        // AUDIO FILE NAME FROM URI
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            OutlinedTextField(
                modifier = modifier.width(280.dp).padding(end = 20.dp),
                value =  audio.audioFileName,
                onValueChange = {
                    //no action due to readonly ppt - never reached
                },
                label = { Text("Selected file: ") },
                readOnly = true
            )

            //SEARCH AUDIO BUTTON
//            Box(modifier = modifier
//                .clip(RoundedCornerShape(30))
//                .padding( top = 5.dp),
//                contentAlignment = Alignment.Center
//            ){
//                Icon(Icons.Rounded.AttachFile,
//                    contentDescription = "Search Audio",
//                    modifier = modifier
//                        .clickable {
//                            if(MediaPlayerFW.player.isPlaying){ MediaPlayerFW.stop() }
//                            audioSearchBTN()
//                            audioFile = addNewAudioScreenObjectStatus.selectedAudioFileName
//                            update()
//                            playerState = MediaPlayerFW.PlayerState.STOP
//                        }
//                        .size(45.dp,55.dp)
//                        .padding(6.dp)
//                        .border(width = 1.dp, color = Green200, shape = RoundedCornerShape(30))
//                )
//            }
        }

        PlayerControls( onTap, update, playerState, context, modifier = modifier)

        Row(modifier = modifier
            .fillMaxWidth()
            ,horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = modifier.padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                onClick = { discardBTN() }
            ) {
                Text(text = stringResource(R.string.discard), color = Black900, fontSize = 17.sp)
            }
            Button(
                modifier = modifier.padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Red300),
                onClick = { deleteBTN(audio) }
            ) {
                Text(text = stringResource(R.string.delete), color = Black900, fontSize = 17.sp)
            }
            Button(
                modifier = modifier.padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = saveBtnColor),
                onClick = { saveBTN(audio) }
            ) {
                Text(text = stringResource(R.string.save), color = Black900, fontSize = 17.sp)
            }
        }
    }
}
