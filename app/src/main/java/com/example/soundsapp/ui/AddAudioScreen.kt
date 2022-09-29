package com.example.soundsapp.ui

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.ui.theme.Green200
import com.example.soundsapp.ui.theme.Red300

object addNewAudioScreenObjectStatus{
    var selectedAudioUri: Uri? = null
    var selectedAudioPath: String? = null
    var selectedAudioUserName: String = "no name yet"
    var selectedAudioFileName: String = ""
//    var playPause : ImageVector = MediaPlayerFW.getIcon()
    var playerState : MediaPlayerFW.PlayerState = MediaPlayerFW.state

    fun reset(){
        this.selectedAudioUri =  null
        this.selectedAudioPath = null
        this.selectedAudioUserName = ""
        this.selectedAudioFileName = ""
        this.playerState = MediaPlayerFW.PlayerState.STOP

    }
}

@Composable
fun AddAudioScreen(audioSearchBTN: () -> Unit,
                   saveBTN: (String) -> Unit,
                   goBackBTN: () -> Unit,
                   context: Context,
                   modifier: Modifier = Modifier
)
{
    var audioName by remember { mutableStateOf(addNewAudioScreenObjectStatus.selectedAudioUserName) }
    var audioFile by remember { mutableStateOf(addNewAudioScreenObjectStatus.selectedAudioFileName) }
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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
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
                addNewAudioScreenObjectStatus.selectedAudioUserName = it
            },
            label = { Text("Enter audio name: ") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            // AUDIO FILE NAME FROM URI
            OutlinedTextField(
                modifier = modifier,
                value =  audioFile,
                onValueChange = {
                                    //no action due to readonly ppt - never reached
                },
                label = { Text("Selected file: ") },
                readOnly = true
            )
            //SEARCH AUDIO BUTTON
            Box(modifier = modifier
                .clip(RoundedCornerShape(30))
                .padding(start = 26.dp, top = 5.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(Icons.Rounded.Add,
                    contentDescription = "Add Audio",
                    modifier = modifier
                        .clickable {
                            if(MediaPlayerFW.player.isPlaying){ MediaPlayerFW.stop() }
                            audioSearchBTN()
                            audioFile = addNewAudioScreenObjectStatus.selectedAudioFileName
                            update()
                            playerState = MediaPlayerFW.PlayerState.STOP
                        }
                        .size(40.dp)
                        .border(width = 3.dp, color = Green200, shape = CircleShape)
                )
            }
        }

        PlayerControls( onTap, update, playerState, context, modifier = modifier)

        Row(modifier = modifier
            .fillMaxWidth()
            ,horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = modifier.padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                onClick = { goBackBTN() }) {
                Text(text = "Discard")
            }
            Button(
                modifier = modifier.padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                onClick = { saveBTN(audioName) }) {
                Text(text = "Save")
            }
        }
    }
}