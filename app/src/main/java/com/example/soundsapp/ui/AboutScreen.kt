package com.example.soundsapp.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen(context: Context, modifier: Modifier = Modifier) {
    Column( modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = modifier.padding(60.dp))
        Text(text = "Developed by:", fontSize = 22.sp)
        Spacer(modifier = modifier.padding(6.dp))
        Text(text = "Federico Wagner", fontSize = 30.sp)
    }
}