package com.example.soundsapp.ui

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import androidx.navigation.ActivityNavigator
import androidx.navigation.ActivityNavigatorExtras
import com.example.soundsapp.BuildConfig
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.db.entity.Group
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.shared.GroupSelector
import com.example.soundsapp.ui.theme.*

object addNewAudioScreenObjectStatus{
    var selectedAudioUri: Uri? = null
    var selectedAudioPath: String? = null
    var selectedAudioUserName: String = ""
    var selectedAudioFileName: String = ""
    var favorite: Boolean = false   //TODO hardcoded value
    var groupId: Long = 0 //NO GROUP

    var updateExternal : () -> Unit = fun(){} // The not null trick ;)

    var playerState : MediaPlayerFW.PlayerState = MediaPlayerFW.state

    fun reset(){
        this.selectedAudioUri =  null
        this.selectedAudioPath = null
        this.selectedAudioUserName = ""
        this.selectedAudioFileName = ""
        this.favorite = false
        this.groupId = 0
        this.playerState = MediaPlayerFW.PlayerState.STOP
        this.updateExternal = fun(){} // The not null trick ;)
    }
    fun isSavable(): Boolean {
        return( this.selectedAudioUri != null &&
                this.selectedAudioUserName != "" &&
                this.selectedAudioFileName != "" )
    }
}


@Composable
fun SelectAudio(groups: List<Group>,
                navigateToGroupManagerScreen: () -> Unit,
                audioSearchBTN: () -> Unit,
                discardBTN: () -> Unit,
                saveBTN: (String) -> Unit,
                showSelectionBTNs : Boolean,
                context: Context,
                modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .height(360.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .background(Black900, RoundedCornerShape(10.dp))
            .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
            .clickable(enabled = false) { }

    ) {
        AddAudioScreen(
            groups,
            navigateToGroupManagerScreen,
            audioSearchBTN,
            discardBTN,
            saveBTN,
            showSelectionBTNs,
            context)
    }
}

@Composable
fun AddAudioScreen(groups: List<Group>,
                   navigateToGroupManagerScreen: () -> Unit,
                   audioSearchBTN: () -> Unit,
                   discardBTN: () -> Unit,
                   saveBTN: (String) -> Unit,
                   showSelectionBTNs : Boolean,
                   context: Context,
                   modifier: Modifier = Modifier
)
{
    var audioName by remember { mutableStateOf(addNewAudioScreenObjectStatus.selectedAudioUserName) }
    var audioFile by remember { mutableStateOf(addNewAudioScreenObjectStatus.selectedAudioFileName) }
    var playerState by remember { mutableStateOf(addNewAudioScreenObjectStatus.playerState) }

    var mSelectedGroup by remember { mutableStateOf(groups[0]) }
    addNewAudioScreenObjectStatus.groupId = mSelectedGroup.groupId

    val onGroupItemClick = fun(group: Group){
        mSelectedGroup = group
        addNewAudioScreenObjectStatus.groupId = group.groupId
    }

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
                addNewAudioScreenObjectStatus.selectedAudioPath.toString(),
                addNewAudioScreenObjectStatus.favorite,
                addNewAudioScreenObjectStatus.groupId
            ),
            onFinish)
        update()
    }
    val saveBtnColor = when ( addNewAudioScreenObjectStatus.isSavable() ) {
        true -> { Green200 }
        false -> { Green500 }
    }

    // For external operations to update this field and trigger recomposition HOTFIX
    val updateFileName = fun(){
        audioFile = addNewAudioScreenObjectStatus.selectedAudioFileName
    }
    addNewAudioScreenObjectStatus.updateExternal = updateFileName

    val focusManager = LocalFocusManager.current

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
                addNewAudioScreenObjectStatus.selectedAudioUserName = it
            },
            label = { Text("Enter audio name: ") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),              // Icon
            keyboardActions = KeyboardActions(onDone =  { focusManager.clearFocus() })  // Action
        )

        // AUDIO FILE NAME FROM URI
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            OutlinedTextField(
                enabled = false,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        if (MediaPlayerFW.player.isPlaying) {
                            MediaPlayerFW.stop()
                        }
                        audioSearchBTN()
                        update()
                    },
                value =  audioFile,
                onValueChange = {
                                    //no action due to readonly ppt - never reached
                },
                label = { Text("Select a file: ") },
                readOnly = true
            )
            Spacer(modifier = modifier.padding(5.dp))
        }

        //GROUP
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            GroupSelector(DataBase.getAllGroups(), onGroupItemClick)

            Spacer(modifier = modifier.padding(5.dp))
            if(showSelectionBTNs) {
                //NEW! BTN
                Text(text = stringResource(R.string.newG), color = Green200, fontSize = 15.sp,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .border(width = 1.dp, color = Green200, shape = RoundedCornerShape(40))
                        .clickable { navigateToGroupManagerScreen() }
                        .padding(3.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = modifier.padding(7.dp))
        PlayerControls( onTap, update, playerState, context, modifier = modifier)
        Spacer(modifier = modifier.padding(7.dp))

        Row(modifier = modifier
            .fillMaxWidth()
            ,horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = modifier,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(40),
                onClick = { discardBTN() }
            ) {
                Text(text = stringResource( R.string.discard), color = Black900, fontSize = 17.sp)
            }
            Button(
                modifier = modifier,
                colors = ButtonDefaults.buttonColors(backgroundColor = saveBtnColor),
                shape = RoundedCornerShape(40),
                onClick = { saveBTN(audioName) }
            ) {
                Text(text = stringResource( R.string.save), color = Black900, fontSize = 17.sp)
            }
        }
        Spacer(modifier = modifier.padding(8.dp))
    }
}
