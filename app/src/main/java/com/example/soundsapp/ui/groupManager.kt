package com.example.soundsapp.ui

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.soundsapp.model.DataBase

enum class GroupManagerState {
    HOME, CREATE, EDIT, DELETE
}

@Composable
fun GroupManager(){

    var screenState by remember { mutableStateOf(GroupManagerState.HOME) }

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
            GroupManagerState.CREATE -> { GroupManagerCREATE() }
            GroupManagerState.EDIT -> { GroupManagerEDIT() }
            GroupManagerState.DELETE -> { GroupManagerDELETE() }
        }
    }
}

@Composable
fun GroupManagerHOME(modifier: Modifier = Modifier){
    Text(text = "In this section you will be able to\n Create, Update & Delete audio groups")
}
@Composable
fun GroupManagerCREATE(modifier: Modifier = Modifier){
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
            Button(onClick = { }) {
                Text(text = "Go back")
            }
            Button(onClick = { DataBase.groupCreateSAFE(groupName) }) {
                Text(text = "Save")
            }
        }
    }

}
@Composable
fun GroupManagerEDIT(modifier: Modifier = Modifier){
    Text(text = "EDIT PAGE")
}
@Composable
fun GroupManagerDELETE(modifier: Modifier = Modifier){
//    Text(text = "DELETE PAGE")
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
            Button(onClick = { }) {
                Text(text = "Go back")
            }
            Button(onClick = { DataBase.deleteGroupByEntitySAFE(mSelectedGroup) }) {
                Text(text = "Delete")
            }
        }
    }
}
