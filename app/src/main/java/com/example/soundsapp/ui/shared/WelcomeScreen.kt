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
import com.example.soundsapp.ui.theme.Gray200
import com.example.soundsapp.ui.theme.Green200
import com.example.soundsapp.ui.theme.White000

@Composable
fun WelcomeScreen( modifier : Modifier = Modifier){

    val big = modifier.padding(28.dp)
    val mid = modifier.padding(12.dp)
    val small = modifier.padding(3.dp)

    val colorStrong = White000 //Green200
    val colorNormal =  Gray200

    val fontSmall = 13.sp

    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()){
        Spacer(big)
//        Text( fontSize = 23.sp, fontWeight = FontWeight(800), color = Green200,
//            text = "Welcome to AudioShare App")
//
//        Spacer(mid)
        Text( fontSize = 23.sp, fontWeight = FontWeight(600), color = colorStrong,
            text = "Store and Share")
        Spacer(small)
        Text( fontSize = fontSmall, fontWeight = FontWeight(300), color = colorNormal,
            text = "your funny audio files")

        Spacer(mid)
        Text( fontSize = 20.sp, fontWeight = FontWeight(600), color = colorStrong,
            text = "Manage Audios into Groups")
        Spacer(small)
        Text( fontSize = fontSmall, fontWeight = FontWeight(300), color = colorNormal,
            text = "Create, Update & Delete groups")

        Spacer(mid)
        Text( fontSize = 20.sp, fontWeight = FontWeight(600), color = colorStrong,
            text = "Favorite your audios")
        Spacer(small)
        Text( fontSize = fontSmall, fontWeight = FontWeight(300), color = colorNormal,
            text = "Keep your audio files closer")

        Spacer(big)
        Text( fontSize = 18.sp, fontWeight = FontWeight(600), color = colorStrong,
            text = "Share your audios with your friends!")
        Spacer(small)
        Text( fontSize = 27.sp, fontWeight = FontWeight(800), color = colorStrong,
            text = "Have fun!")
    }
}