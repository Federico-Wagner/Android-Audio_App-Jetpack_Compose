package com.example.soundsapp.ui.shared

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundsapp.ui.theme.Green200

@Composable
fun WelcomeScreen( modifier : Modifier = Modifier){

    val big = modifier.padding(30.dp)
    val mid = modifier.padding(12.dp)
    val small = modifier.padding(5.dp)

    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()){
        Spacer(big)
        Text( fontSize = 23.sp, fontWeight = FontWeight(800), color = Green200,
            text = "Welcome to AudioShare App")

        Spacer(mid)
        Text( fontSize = 23.sp, fontWeight = FontWeight(600), color = Green200,
            text = "Store and Share")
        Spacer(small)
        Text( fontSize = 15.sp, fontWeight = FontWeight(300),
            text = "your funny audio files")

        Spacer(mid)
        Text( fontSize = 20.sp, fontWeight = FontWeight(600), color = Green200,
            text = "Manage Audios into Groups")
        Spacer(small)
        Text( fontSize = 15.sp, fontWeight = FontWeight(300),
            text = "Create, Update & Delete groups")

        Spacer(mid)
        Text( fontSize = 20.sp, fontWeight = FontWeight(600), color = Green200,
            text = "Favorite audios")
        Spacer(small)
        Text( fontSize = 15.sp, fontWeight = FontWeight(300),
            text = "Keep your audio files closer")

        Spacer(big)
        Text( fontSize = 18.sp, fontWeight = FontWeight(600), color = Green200,
            text = "Share your audios with your friends!")
        Spacer(small)
        Text( fontSize = 25.sp, fontWeight = FontWeight(800), color = Green200,
            text = "Have fun!")
    }
}