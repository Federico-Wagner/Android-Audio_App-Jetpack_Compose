package com.example.soundsapp.ui.shared

import android.content.ContentResolver
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.soundsapp.R

@Composable
fun DropDownSelector(toGroupManager: () -> Unit,
                     toAboutScreen: () -> Unit,
                     context: Context,
                     modifier : Modifier = Modifier){
    var mExpanded by remember { mutableStateOf(false) }
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowDown
    else
        Icons.Sharp.MoreVert

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            icon,
            contentDescription = "group Manager",
            modifier = modifier
                .clickable { mExpanded = !mExpanded }
                .size(40.dp)
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
        ) {
            DropdownMenuItem(onClick = {
                mExpanded = false
                toGroupManager()
            }) {
                Text(text = "Group Manager")
            }
            DropdownMenuItem(onClick = {
                mExpanded = false
                toAboutScreen()
            }) {
                Text(text = "About")
            }
        }
    }





}