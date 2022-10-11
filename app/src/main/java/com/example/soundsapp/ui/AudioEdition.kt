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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.db.entity.Group
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.theme.*


object editAudioObjectStatus{
    var selectedAudio : Audio? = null
    var originalAudioName : String = ""
    var originalAudioGroup : Long? = null
    var playerState : MediaPlayerFW.PlayerState = MediaPlayerFW.state

    fun reset() {
        this.selectedAudio = null
        this.originalAudioName = ""
        this.originalAudioGroup = null
        this.playerState = MediaPlayerFW.PlayerState.STOP
    }
    fun isSavable(): Boolean {
        return ( (this.selectedAudio!!.audioUserName != this.originalAudioName) ||
                (this.selectedAudio!!.groupId!!.toInt() != this.originalAudioGroup!!.toInt()) )
    }
    fun setAudio(audio: Audio) {
        this.selectedAudio = audio
        this.originalAudioName = audio.audioUserName
        this.originalAudioGroup = audio.groupId
    }
}

@Composable
fun EditAudio(audio : Audio,
              groups: List<Group>,
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
            .clickable(enabled = false) { }

    ) {
        EditAudioLayOut(
            audio,
            groups,
            discardBTN,
            saveBTN,
            deleteBTN,
            context)
    }
}

@Composable
fun EditAudioLayOut(audio : Audio,
                    groups: List<Group>,
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

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    val getGroupName =  fun(): String{
        val group = groups.find { it.groupId == editAudioObjectStatus.selectedAudio!!.groupId }
        return group!!.groupName
    }
    mSelectedText = getGroupName()


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


    //DEFAULT STAR
    var starIcon : ImageVector = Icons.Default.StarBorder
    var starColor : Color = White300
    // DEFAULT / FAV STAR SELECTION
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
    updateStar()

    Column(
        modifier = modifier
            .height(400.dp)
            .padding(horizontal = 10.dp),
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
                    .width(270.dp)
                    .padding(end = 10.dp),
                value = audioName,
                onValueChange = {
                    audioName = it
                    editAudioObjectStatus.selectedAudio!!.audioUserName = it
                },
                label = { Text("Audio name: ") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Spacer(modifier= modifier.padding(5.dp))
            Icon(starIcon, contentDescription = "Favorite star", tint = starColor,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        editAudioObjectStatus.selectedAudio!!.favorite =
                            !editAudioObjectStatus.selectedAudio!!.favorite
                        updateStar()
                        DataBase.updateAudioInDB(editAudioObjectStatus.selectedAudio!!)
                    })
        }
        // AUDIO FILE NAME FROM URI
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            OutlinedTextField(
                modifier = modifier
                    .width(280.dp)
                    .padding(end = 20.dp),
                value =  editAudioObjectStatus.selectedAudio!!.audioFileName,
                onValueChange = {
                    //no action due to readonly ppt - never reached
                },
                label = { Text("Selected file: ") },
                readOnly = true
            )
        }
        //GROUP
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            OutlinedTextField(
                modifier = modifier
                    .width(280.dp)
                    .padding(end = 20.dp),
                value =  mSelectedText,
                onValueChange = { },
                label = { Text("Audio group") },
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { mExpanded = !mExpanded })
                }
            )
            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
            ) {
                groups.forEach { group ->
                    DropdownMenuItem(onClick = {
                        mSelectedText = group.groupName
                        editAudioObjectStatus.selectedAudio!!.groupId = group.groupId
                        mExpanded = false
                    }) {
                        Text(text = group.groupName)
                    }
                }
            }
        }
        Spacer(modifier = modifier.padding(8.dp))
        PlayerControls( onTap, update, playerState, context, modifier = modifier)

        Row(modifier = modifier
            .fillMaxWidth()
            ,horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(
                modifier = modifier.padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(40),
                onClick = { discardBTN() }
            ) {
                Text(text = stringResource(R.string.discard), color = Black900, fontSize = 17.sp)
            }
            Button(
                modifier = modifier.padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Red300),
                shape = RoundedCornerShape(40),
                onClick = { deleteBTN(editAudioObjectStatus.selectedAudio!!) }
            ) {
                Text(text = stringResource(R.string.delete), color = Black900, fontSize = 17.sp)
            }
            Button(
                modifier = modifier.padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = saveBtnColor),
                shape = RoundedCornerShape(40),
                onClick = {
                    if(editAudioObjectStatus.isSavable()){ saveBTN(editAudioObjectStatus.selectedAudio!!) }
                }
            ) {
                Text(text = stringResource(R.string.save), color = Black900, fontSize = 17.sp)
            }
        }
    }
}
