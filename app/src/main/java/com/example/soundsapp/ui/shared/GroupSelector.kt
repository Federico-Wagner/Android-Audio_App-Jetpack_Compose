package com.example.soundsapp.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.soundsapp.db.entity.Group


@Composable
fun GroupSelector(groups: List<Group>,
                  onGroupItemClick: (Group) -> Unit,
                  modifier: Modifier = Modifier) {
    var mExpanded by remember { mutableStateOf(false) }
    var mSelectedGroup by remember { mutableStateOf(groups[0]) }
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(0.7F),
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
                    onGroupItemClick(group)
                    mSelectedGroup = group
                    mExpanded = false
                }) {
                    Text(text = group.groupName)
                }
            }
        }
    }
}
