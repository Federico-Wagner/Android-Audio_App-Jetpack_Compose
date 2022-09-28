package com.example.soundsapp.ui

import android.content.Context
import android.media.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.ui.theme.Green200
import com.example.soundsapp.ui.theme.Red100

//object PlayerControls {
//    var playPause : ImageVector = MediaPlayerFW.getIcon()
//}


@Composable
fun PlayerControls(playPause : ImageVector, update: ()-> Unit, context: Context,
                   modifier: Modifier = Modifier
){
//    var playPause by remember { mutableStateOf(PlayerControls.playPause) }
//
//    val update = fun(){
//        playPause = MediaPlayerFW.getIcon()
//    }

    Row {
        StopBTN(update, context = context, modifier= modifier)
        Spacer(modifier = Modifier.padding(end = 10.dp))
        PlayBTN(update, playPause, context = context, modifier= modifier)
    }
}

@Composable
fun PlayBTN(update : () -> Unit,
            playPause : ImageVector,
            context: Context,
            modifier: Modifier = Modifier
){
    Box(modifier = modifier
        .size(55.dp)
        .clip(RoundedCornerShape(50)),
    ){
        Icon(playPause, contentDescription = "Play",
            modifier = modifier
                .clickable {
                    MediaPlayerFW.tap(
                        context, Audio(
                            0, addNewAudioScreenObjectStatus.selectedAudioUserName,
                            addNewAudioScreenObjectStatus.selectedAudioFileName,
                            addNewAudioScreenObjectStatus.selectedAudioUri.toString(),
                            addNewAudioScreenObjectStatus.selectedAudioPath.toString()
                        )
                    )
                    update()
                }
                .size(55.dp)
                .clip(RoundedCornerShape(50)),
            tint = Green200
        )
    }
}
@Composable
fun StopBTN(update : () -> Unit,
            context: Context,
            modifier: Modifier = Modifier
){
    Box(modifier = modifier
        .size(55.dp)
        .clip(RoundedCornerShape(50)),
    ){
        Icon(Icons.Rounded.Stop, contentDescription = "Play",
            modifier = modifier
                .clickable {
                    MediaPlayerFW.stop()
                    update()
                }
                .size(55.dp)
                .clip(RoundedCornerShape(50)),
            tint = Red100
        )
    }
}
