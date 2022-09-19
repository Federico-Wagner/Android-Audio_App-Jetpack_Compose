package com.example.soundsapp.ui

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.soundsapp.helpers.shareSound
import com.example.soundsapp.model.Sound
import com.example.soundsapp.ui.theme.Green200
import com.example.soundsapp.R
import com.example.soundsapp.helpers.AudioPlayer
import com.example.soundsapp.helpers.addNewAudio


@Composable
fun SoundApp(sounds: List<Sound>, activity: Activity, modifier : Modifier = Modifier){
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            Row{
                Image(painter = painterResource(R.drawable.icon), contentDescription = "iconImg",
                    modifier
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .size(45.dp)
                        .clip(RoundedCornerShape(50)))
                Text(text = "Sounds App",
                    modifier = modifier.padding(10.dp),
                    style = MaterialTheme.typography.h4)
            }
        }
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            SoundsList(sounds)
            Spacer(modifier = modifier.padding(top = 15.dp))
            AddBtn(activity)
            Spacer(modifier = modifier.padding(top = 25.dp))
            Text(text = "Developed by Federico Wagner",
                style = MaterialTheme.typography.h6)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SoundsList(sounds: List<Sound>) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)){
        items(sounds){sound -> SoundCard(sound)}
    }
}

@Composable
fun SoundCard(sound : Sound = Sound("Preview", 5),
              modifier : Modifier = Modifier ) {
    val intentContext = LocalContext.current
    Card(modifier = Modifier
        .padding(8.dp)
        .height(60.dp)
        .clip(RoundedCornerShape(30)),
        elevation = 5.dp
    ){
        Row(modifier = modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically){
            Box(modifier = modifier
                .size(55.dp)
                .clip(RoundedCornerShape(50)),
            ){
                Icon(Icons.Rounded.PlayArrow, contentDescription = "Play",
                    modifier = modifier
                        .clickable {
                            AudioPlayer.play(sound, intentContext)
                        }
                        .size(55.dp)
                        .clip(RoundedCornerShape(50)),
                    tint = Green200
                )
            }
            Text(text = sound.stringResourceId, modifier = modifier.width(80.dp))
            Box(modifier = modifier
                .clip(RoundedCornerShape(50)),
            ){
                Icon(
                    Icons.Rounded.Share,
                    contentDescription = "share",
                    modifier = modifier
                        .clickable {
                            shareSound(intentContext, sound)
                        }
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun AddBtn(activity : Activity, modifier : Modifier = Modifier){
    val intentContext = LocalContext.current
    Box(modifier = modifier
        .clip(RoundedCornerShape(50)),
    ){
        Icon(Icons.Rounded.Add,
            contentDescription = "Add Audio",
            modifier = modifier
                .clickable { addNewAudio(intentContext, activity) }
                .size(60.dp)
                .border(width = 3.dp, color = Green200, shape = CircleShape)
        )
    }
}