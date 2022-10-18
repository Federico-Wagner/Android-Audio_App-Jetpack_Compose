package com.example.soundsapp.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Group
import com.example.soundsapp.helpers.ToastHelper
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.shared.GroupSelector

enum class GroupManagerState {
    HOME, CREATE, EDIT, DELETE
}

@Composable
fun GroupManager(context: Context){
    var screenState by remember { mutableStateOf(GroupManagerState.HOME) }
    val goBack = fun(){
        screenState = GroupManagerState.HOME
    }

    Column(){
        Row(){
            Button(onClick = { screenState = GroupManagerState.CREATE }) {
                Text(text = "Create")
            }
            Button(onClick = {  screenState = GroupManagerState.EDIT }) {
                Text(text = "Edit")
            }
            Button(onClick = {  screenState = GroupManagerState.DELETE }) {
                Text(text = "Delete")
            }
        }

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
    Text(text = "In this section you will be able to\n Create, Update & Delete audio groups")
}
@Composable
fun GroupManagerCREATE(goBack: () -> Unit, context: Context,  modifier: Modifier = Modifier){
    var groupName by remember { mutableStateOf("") }

    Column() {
        OutlinedTextField(
            modifier = modifier
                .width(270.dp)
                .padding(end = 10.dp),
            value = groupName,
            onValueChange = {
                groupName = it
            },
            label = { Text("New Group Name") }
        )
        Row() {
            Button(onClick = { goBack() }) {
                Text(text = "Go back")
            }
            Button(onClick = {
                if(DataBase.groupCreateSAFE(groupName, context)){
                    goBack()
                }},
                enabled = (groupName != "")) {
                Text(text = "Save")
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

    Column() {
        GroupSelector(DataBase.getAllGroups(), onGroupItemClick, 280.dp)

        OutlinedTextField(
            modifier = modifier
                .width(280.dp)
                .padding(end = 20.dp),
            value = newGroupName,
            onValueChange = { newGroupName = it },
            label = { Text("New Group Name") },
        )
        Row() {
            Button(onClick = { goBack() }) {
                Text(text = "Go back")
            }
            Button(onClick = {
                if(DataBase.updateByEntitySAFE(mSelectedGroup, newGroupName)){
                    ToastHelper.sendToastMesage(toastMsg, context)
                    goBack()
                }},
            enabled = (newGroupName != "" && newGroupName != mSelectedGroup.groupName)) {
                Text(text = "Save")
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

    Column() {
        GroupSelector(DataBase.getAllGroups(), onGroupItemClick, 280.dp)

        Row() {
            Button(onClick = { goBack() }) {
                Text(text = "Go back")
            }
            Button(onClick = {
                if(DataBase.deleteGroupByEntitySAFE(mSelectedGroup)){
                    ToastHelper.sendToastMesage(toastMsg, context)
                    goBack()
            } }) {
                Text(text = "Delete")
            }
        }
    }
}
