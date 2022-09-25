package com.example.soundsapp.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.soundsapp.addNewAudioScreenObjectStatus
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.ui.theme.Green200


@Composable
fun PlayerControls(context: Context,
                   modifier: Modifier = Modifier
){
    var state : MediaPlayerFW.PlayerState = MediaPlayerFW.state

    Box(modifier = modifier
        .size(55.dp)
        .clip(RoundedCornerShape(50)),
    ){
        Icon(Icons.Rounded.PlayArrow, contentDescription = "Play",
            modifier = modifier
                .clickable {
//                    MediaPlayerFW.tap(context, addNewAudioScreenObjectStatus.selectedAudioUri)
                    MediaPlayerFW.tap(context, Audio(0, addNewAudioScreenObjectStatus.selectedAudioUserName
                        , addNewAudioScreenObjectStatus.selectedAudioFileName,
                        addNewAudioScreenObjectStatus.selectedAudioUri.toString())
                    )
                    state = MediaPlayerFW.state
                }
                .size(55.dp)
                .clip(RoundedCornerShape(50)),
            tint = Green200
        )
    }
}


//Icons.Rounded.PlayArrow
//Icons.Rounded.Pause
//Icons.Rounded.Stop