package com.example.soundsapp.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.theme.*


object editAudioObjectStatus{
    var selectedAudio : Audio? = null
    var originalAudioName : String = ""
    var playerState : MediaPlayerFW.PlayerState = MediaPlayerFW.state

    fun reset() {
        this.selectedAudio = null
        this.playerState = MediaPlayerFW.PlayerState.STOP
    }
    fun isSavable(): Boolean {
        return ( this.selectedAudio!!.audioUserName != this.originalAudioName )
    }
    fun setAudio(audio: Audio) {
        this.selectedAudio = audio
        this.originalAudioName = audio.audioUserName
    }
}

@Composable
fun EditAudio(  audio : Audio,
                discardBTN: () -> Unit,
                saveBTN: (Audio) -> Unit,
                deleteBTN: (Audio) -> Unit,
                context: Context,
                modifier: Modifier = Modifier
){
    editAudioObjectStatus.setAudio(audio)

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
    var audioName by remember { mutableStateOf(editAudioObjectStatus.selectedAudio!!.audioUserName) }
    var playerState by remember { mutableStateOf(editAudioObjectStatus.playerState) }
    var favoriteState by remember { mutableStateOf(editAudioObjectStatus.selectedAudio!!.favorite) }



    val onFinish = fun(){
        playerState = MediaPlayerFW.state
    }
    val update = fun(){
        playerState = MediaPlayerFW.state
    }
    val onTap = fun(){
        MediaPlayerFW.tap(
            context,
            editAudioObjectStatus.selectedAudio!!,
            onFinish
        )
        update()
    }
    val saveBtnColor = when ( editAudioObjectStatus.isSavable() ) {
        true -> { Green200 }
        false -> { Green500 }
    }

    var starIcon : ImageVector
    var starColor : Color
    when (favoriteState) {
        true -> {
            starIcon = Icons.Default.Star
            starColor = Gold600
        }
        false -> {
            starIcon = Icons.Default.StarBorder
            starColor = White300
        }
    }

    val updateStar = fun(){
        favoriteState = editAudioObjectStatus.selectedAudio!!.favorite
        when (favoriteState) {
            true -> {
                starIcon = Icons.Default.Star
                starColor = Gold600
            }
            false -> {
                starIcon = Icons.Default.StarBorder
                starColor = White300
            }
        }
    }

    Column(
        modifier = modifier
            .height(400.dp)
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // AUDIO NAME FROM USER
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                modifier = modifier
                    .width(280.dp).padding(end = 20.dp),
                value = audioName,
                onValueChange = {
                    audioName = it
                    editAudioObjectStatus.selectedAudio!!.audioUserName = it
                },
                label = { Text("Audio name: ") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Icon(starIcon, contentDescription = "Favorite star", tint = starColor,
                modifier = Modifier
                    .clickable {
                        editAudioObjectStatus.selectedAudio!!.favorite = !editAudioObjectStatus.selectedAudio!!.favorite
                        updateStar()
                        DataBase.updateAudioInDB(editAudioObjectStatus.selectedAudio!!, context)
                    })
        }
        // AUDIO FILE NAME FROM URI
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            OutlinedTextField(
                modifier = modifier.width(280.dp).padding(end = 20.dp),
                value =  editAudioObjectStatus.selectedAudio!!.audioFileName,
                onValueChange = {
                    //no action due to readonly ppt - never reached
                },
                label = { Text("Selected file: ") },
                readOnly = true
            )
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
                onClick = { deleteBTN(editAudioObjectStatus.selectedAudio!!) }
            ) {
                Text(text = stringResource(R.string.delete), color = Black900, fontSize = 17.sp)
            }
            Button(
                modifier = modifier.padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = saveBtnColor),
                onClick = {
                    if(editAudioObjectStatus.isSavable()){ saveBTN(editAudioObjectStatus.selectedAudio!!) }
                }
            ) {
                Text(text = stringResource(R.string.save), color = Black900, fontSize = 17.sp)
            }
        }
    }
}
