package com.example.soundsapp.ui

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.soundsapp.model.DataBase

enum class GroupManagerState {
    HOME, CREATE, EDIT, DELETE
}

@Composable
fun GroupManager(){

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
            GroupManagerState.HOME -> { GroupManagerHOME() }
            GroupManagerState.CREATE -> { GroupManagerCREATE(goBack) }
            GroupManagerState.EDIT -> { GroupManagerEDIT(goBack) }
            GroupManagerState.DELETE -> { GroupManagerDELETE(goBack) }
        }
    }
}

@Composable
fun GroupManagerHOME(modifier: Modifier = Modifier){
    Text(text = "In this section you will be able to\n Create, Update & Delete audio groups")
}
@Composable
fun GroupManagerCREATE(goBack: () -> Unit, modifier: Modifier = Modifier){
//    Text(text = "CREATE PAGE")
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
                if(DataBase.groupCreateSAFE(groupName)){
                    goBack()
//                    val text = "Group created"
//                    val duration = Toast.LENGTH_SHORT
//                    val toast = Toast.makeText(context, text, duration)
//                    toast.show()
                }},
                enabled = (groupName != "")) {
                Text(text = "Save")
            }
        }
    }

}
@Composable
fun GroupManagerEDIT(goBack: () -> Unit, modifier: Modifier = Modifier){
    Text(text = "EDIT PAGE")
    val groups = DataBase.getAllGroups()
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    // Create a string value to store the selected city
    var mSelectedGroup by remember { mutableStateOf(groups[0]) }
    var newGroupName by remember { mutableStateOf("") }
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    //GROUP
    Column() {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                modifier = modifier
                    .width(280.dp)
                    .padding(end = 20.dp),
                value = mSelectedGroup.groupName,
                onValueChange = { },
                label = { Text("Group Name") },
                trailingIcon = {
                    Icon(icon, "contentDescription",
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
                        mSelectedGroup = group
                        mExpanded = false
                    }) {
                        Text(text = group.groupName)
                    }
                }
            }
        }
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
                    goBack()
                }},
            enabled = (newGroupName != "" && newGroupName != mSelectedGroup.groupName)) {
                Text(text = "Save")
            }
        }
    }
}
@Composable
fun GroupManagerDELETE(goBack: () -> Unit, modifier: Modifier = Modifier){
    val groups = DataBase.getAllGroups()
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    // Create a string value to store the selected city
    var mSelectedGroup by remember { mutableStateOf(groups[0]) }
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    //GROUP
    Column() {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                modifier = modifier
                    .width(280.dp)
                    .padding(end = 20.dp),
                value = mSelectedGroup.groupName,
                onValueChange = { },
                label = { Text("Group Name") },
                trailingIcon = {
                    Icon(icon, "contentDescription",
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
                        mSelectedGroup = group
                        mExpanded = false
                    }) {
                        Text(text = group.groupName)
                    }
                }
            }
        }

        Row() {
            Button(onClick = { goBack() }) {
                Text(text = "Go back")
            }
            Button(onClick = {
                if(DataBase.deleteGroupByEntitySAFE(mSelectedGroup)){
                    goBack()
            } }) {
                Text(text = "Delete")
            }
        }
    }
}
