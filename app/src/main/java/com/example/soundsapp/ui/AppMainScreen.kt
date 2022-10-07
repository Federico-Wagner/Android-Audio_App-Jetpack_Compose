package com.example.soundsapp.ui

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundsapp.ui.theme.Green200
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.helpers.shareSound
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.theme.Black900
import com.example.soundsapp.ui.theme.Purple700


@Composable
fun MainScreen( soundsDBx: List<Audio>,
                showHidePopupBTN: () -> Unit,
                navigateToAudioDetail: () -> Unit,
                context: Context,
                modifier : Modifier = Modifier){
    val soundsDB by remember { mutableStateOf(soundsDBx) }  //TODO fix to update on resume

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
                Icon(
                    Icons.Sharp.Search,
                    contentDescription = "search",
                    modifier = modifier
                        .clickable { }
                        .size(60.dp)
                        .padding(start = 30.dp, top = 15.dp)
                )
            }
        },
        bottomBar = {
            Column(
                Modifier.fillMaxWidth().background(color = Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
            Spacer(modifier = modifier.padding(top = 15.dp))
                ShowPopupBTN(showHidePopupBTN)
//            DeleteAll()
//            AddBtn(addAudioBTN)
            Spacer(modifier = modifier.padding(top = 25.dp))
            Text(text = "Developed by Federico Wagner",
                fontSize = 15.sp, modifier= Modifier.padding(bottom = 5.dp))
            }
            Spacer(modifier = modifier.padding(bottom = 25.dp))
        }
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            SoundsList(soundsDB,navigateToAudioDetail, context)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SoundsList(soundsDB: List<Audio>,
               navigateToAudioDetail: () -> Unit,
               context: Context) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)){
        items(soundsDB){audio -> SoundCardDB(audio,navigateToAudioDetail, context)}
    }
}

@Composable
fun SoundCardDB( audio: Audio,
                 navigateToAudioDetail: () -> Unit,
                 context: Context,
              modifier : Modifier = Modifier ) {
    var playPause by remember { mutableStateOf(Icons.Rounded.PlayArrow) }

    val onFinish = fun(){
        playPause = MediaPlayerFW.getIcon()
    }
    val onTap = fun(){
        MediaPlayerFW.tap(context, audio, onFinish)
        playPause = MediaPlayerFW.getIcon()
    }

    Card(modifier = Modifier
        .padding(8.dp)
        .height(60.dp)
        .clip(RoundedCornerShape(30)),
        elevation = 5.dp
    ){
        Row(modifier = modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically){

            PlayBTN(playPause, null, onTap, context = context, modifier= modifier)

            Text(text = audio.audioUserName,
                modifier = modifier
                    .width(70.dp)
                    .clickable {
                        editAudioObjectStatus.selectedAudio = audio
                        navigateToAudioDetail()
                    })

            Box(modifier = modifier
                .clip(RoundedCornerShape(50)),
            ){
                Icon(
                    Icons.Rounded.Share,
                    contentDescription = "share",
                    modifier = modifier
                        .clickable {
                            shareSound(context, audio)
                        }
                        .size(28.dp)
                        .padding(end = 5.dp)
                )
            }
        }
    }
}

@Composable
fun ShowPopupBTN(showHidePopupBTN : () -> Unit , modifier : Modifier = Modifier){
    Box(modifier = modifier
        .clip(RoundedCornerShape(50)),
    ){
        Icon(Icons.Rounded.Add,
            contentDescription = "Show Popup",
            modifier = modifier
                .clickable {
                    showHidePopupBTN()
                }
                .size(50.dp)
                .border(width = 3.dp, color = Green200, shape = CircleShape)
        )
    }
}

@Composable
fun DeleteAll(modifier : Modifier = Modifier){
    Box(modifier = modifier
        .clip(RoundedCornerShape(50)),
    ){
        Icon(Icons.Rounded.Add,
            contentDescription = "Add Audio",
            modifier = modifier
                .clickable {
                    DataBase.deleteAllRecords()
                }
                .size(60.dp)
                .border(width = 3.dp, color = Purple700, shape = CircleShape)
        )
    }
}

