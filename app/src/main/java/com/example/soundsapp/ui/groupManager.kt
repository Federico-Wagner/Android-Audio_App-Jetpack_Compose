package com.example.soundsapp.ui

import android.content.Context
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Group
import com.example.soundsapp.helpers.ToastHelper
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.shared.GroupSelector
import com.example.soundsapp.ui.theme.Black900
import com.example.soundsapp.ui.theme.Green300
import com.example.soundsapp.ui.theme.Red300

enum class GroupManagerState {
    HOME, CREATE, EDIT, DELETE
}

@Composable
fun GroupManager(context: Context, modifier: Modifier = Modifier){
    var screenState by remember { mutableStateOf(GroupManagerState.HOME) }
    val goBack = fun(){
        screenState = GroupManagerState.HOME
    }
    val btnColor = Green300

    Column(){
        Spacer(modifier = modifier.padding(15.dp))
        Row(modifier = modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button( shape = RoundedCornerShape(40),
                    onClick = { screenState = GroupManagerState.CREATE },
                    colors = ButtonDefaults.buttonColors(backgroundColor = btnColor)) {
                Text(text = "Create", fontSize = 17.sp)
            }
            Button( shape = RoundedCornerShape(40),
                    onClick = {  screenState = GroupManagerState.EDIT },
                    colors = ButtonDefaults.buttonColors(backgroundColor = btnColor)) {
                Text(text = "Edit", fontSize = 17.sp)
            }
            Button( shape = RoundedCornerShape(40),
                    onClick = {  screenState = GroupManagerState.DELETE },
                    colors = ButtonDefaults.buttonColors(backgroundColor = btnColor)) {
                Text(text = "Delete", fontSize = 17.sp)
            }
        }
        Spacer(modifier = modifier.padding(12.dp))

        when (screenState){
            GroupManagerState.HOME -> { GroupManagerHOME(context) }
            GroupManagerState.CREATE -> { GroupManagerCREATE(goBack, context) }
            GroupManagerState.EDIT -> { GroupManagerEDIT(goBack, context) }
            GroupManagerState.DELETE -> { GroupManagerDELETE(goBack, context) }
        }
    }
}

@Composable
fun GroupManagerHOME(context: Context, modifier: Modifier = Modifier){
    Column( horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()){
        Spacer(modifier = modifier.padding(5.dp))
        Text(text = "In this section you will be able to:")
        Spacer(modifier = modifier.padding(5.dp))
        Text(text = "Create, Update & Delete", fontSize = 23.sp, fontWeight = FontWeight(400))
        Spacer(modifier = modifier.padding(5.dp))
        Text(text = "audio groups")
    }
}
@Composable
fun GroupManagerCREATE(goBack: () -> Unit, context: Context,  modifier: Modifier = Modifier){
    var groupName by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = modifier
                .width(270.dp)
                .padding(end = 10.dp),
            value = groupName,
            onValueChange = {
                groupName = it
            },
            label = { Text("New Group Name") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),              // Icon
            keyboardActions = KeyboardActions(onDone =  { focusManager.clearFocus() })  // Action
        )
        Spacer(modifier = modifier.padding(15.dp))
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
            Button( shape = RoundedCornerShape(40),
                    onClick = { goBack() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text(text = "Go back", color = Black900, fontSize = 17.sp)
            }
            Button( colors = ButtonDefaults.buttonColors(backgroundColor = Green300),
                    shape = RoundedCornerShape(40),
                    onClick = {
                if(DataBase.groupCreateSAFE(groupName, context)){
                    goBack()
                }},
                    enabled = (groupName != "")) {
                Text(text = "Save", fontSize = 17.sp)
            }
        }
    }

}
@Composable
fun GroupManagerEDIT(goBack: () -> Unit, context: Context, modifier: Modifier = Modifier){
    val groups = DataBase.getAllGroups()
    var mSelectedGroup by remember { mutableStateOf(groups[0]) }
    var newGroupName by remember { mutableStateOf("") }
    val onGroupItemClick = fun(group: Group){
    mSelectedGroup = group
    }
    val toastMsg = stringResource(id = R.string.groupEdit)
    val focusManager = LocalFocusManager.current

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()) {
        GroupSelector(DataBase.getAllGroups(), onGroupItemClick)
        Spacer(modifier = modifier.padding(8.dp))
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(0.7F),
            value = newGroupName,
            onValueChange = { newGroupName = it },
            label = { Text("New Group Name") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),              // Icon
            keyboardActions = KeyboardActions(onDone =  { focusManager.clearFocus() })  // Action
        )
        Spacer(modifier = modifier.padding(15.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Button( shape = RoundedCornerShape(40),
                    onClick = { goBack() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text(text = "Go back", color = Black900, fontSize = 17.sp)
            }
            Button( shape = RoundedCornerShape(40),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Green300),
                    onClick = {
                        if(DataBase.updateByEntitySAFE(mSelectedGroup, newGroupName)){
                            ToastHelper.sendToastMesage(toastMsg, context)
                            goBack()
                        }},
                    enabled = (newGroupName != "" && newGroupName != mSelectedGroup.groupName)) {
                Text(text = "Save", fontSize = 17.sp)
            }
        }
    }
}
@Composable
fun GroupManagerDELETE(goBack: () -> Unit, context: Context, modifier: Modifier = Modifier){
    val groups = DataBase.getAllGroups()
    var mSelectedGroup by remember { mutableStateOf(groups[0]) }
    val onGroupItemClick = fun(group: Group){
        mSelectedGroup = group
    }
    val toastMsg = stringResource(id = R.string.groupDeleted)

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()) {
        GroupSelector(DataBase.getAllGroups(), onGroupItemClick)
        Spacer(modifier = modifier.padding(15.dp))
        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Button( shape = RoundedCornerShape(40),
                    onClick = { goBack() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text(text = "Go back", color = Black900, fontSize = 17.sp)
            }
            Button( shape = RoundedCornerShape(40),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red300),
                    onClick = {
                if(DataBase.deleteGroupByEntitySAFE(mSelectedGroup)){
                    ToastHelper.sendToastMesage(toastMsg, context)
                    goBack()
            } }) {
                Text(text = "Delete", fontSize = 17.sp)
            }
        }
    }
}
