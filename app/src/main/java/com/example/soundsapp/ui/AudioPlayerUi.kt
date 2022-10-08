package com.example.soundsapp.ui

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.ui.theme.*


@Composable
fun PlayerControls( onTap: ()-> Unit,
                    update: ()-> Unit,
                    playerState : MediaPlayerFW.PlayerState,
                    context: Context,
                    modifier: Modifier = Modifier
){
    Row(modifier = modifier
        .border(1.dp, color = Color.White, RoundedCornerShape(30))
        .padding(horizontal = 10.dp)
    ){
        StopBTN(update, playerState, context = context, modifier= modifier)
        Spacer(modifier = Modifier.padding(end = 10.dp))
        PlayBTN(null, playerState, onTap, context = context, modifier= modifier)
    }
}

@Composable
fun PlayBTN(playPause : ImageVector?,
            playerState : MediaPlayerFW.PlayerState?,
            onTap: () -> Unit,
            context: Context,
            modifier: Modifier = Modifier
){
    val icon : ImageVector
    val color : Color
    val enabled : Boolean

    if(playPause != null && playerState == null ){
        // MAIN SCREEN
         icon = playPause
         color = Green200
        enabled = true
    }else {
        // ADD AUDIO SCREEN
        enabled = addNewAudioScreenObjectStatus.selectedAudioUri != null ||
                editAudioObjectStatus.selectedAudio != null
        icon = when (playerState) {
            MediaPlayerFW.PlayerState.STOP -> { Icons.Rounded.PlayArrow }
            MediaPlayerFW.PlayerState.PLAY -> { Icons.Rounded.Pause }
            MediaPlayerFW.PlayerState.PAUSE -> { Icons.Rounded.PlayArrow }
            else -> {Icons.Rounded.Error}
        }
        if(enabled) {
            color = when (playerState) {
                MediaPlayerFW.PlayerState.STOP -> { Green200 }
                MediaPlayerFW.PlayerState.PLAY -> { White300 }
                MediaPlayerFW.PlayerState.PAUSE -> { Green200 }
                else -> {Green200}
            }
        }else{ //BTN disabled
            color = Green500
        }
    }

    Box(modifier = modifier
        .size(55.dp)
        .clip(RoundedCornerShape(50)),
    ){
        Icon(icon, contentDescription = "Play",
            modifier = modifier
                .clickable {
                    if(enabled) { onTap() }
                }
                .size(55.dp)
                .clip(RoundedCornerShape(50)),
            tint = color
        )
    }
}

@Composable
fun StopBTN(update : () -> Unit,
            playerState : MediaPlayerFW.PlayerState,
            context: Context,
            modifier: Modifier = Modifier
){
     val color = when ( playerState ) {
        MediaPlayerFW.PlayerState.STOP -> { Red300 }
        MediaPlayerFW.PlayerState.PLAY -> { Red100 }
        MediaPlayerFW.PlayerState.PAUSE -> { Red100 }
    }

    Box(modifier = modifier
        .size(55.dp)
        .clip(RoundedCornerShape(50)),
    ){
        Icon(Icons.Rounded.Stop, contentDescription = "Play",
            modifier = modifier
                .clickable {
                    if (MediaPlayerFW.state != MediaPlayerFW.PlayerState.STOP) {
                        MediaPlayerFW.stop()
                        update()
                    }
                }
                .size(55.dp)
                .clip(RoundedCornerShape(50)),
            tint = color
        )
    }
}
