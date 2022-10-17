package com.example.soundsapp.ui

import android.content.ContentResolver
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorGroup
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundsapp.ui.theme.Green200
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.db.entity.Group
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.helpers.shareSound
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.theme.Black900
import com.example.soundsapp.ui.theme.Gold600
import com.example.soundsapp.ui.theme.Purple700
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode


@Composable
fun MainScreen( soundsDBx: List<Audio>,
                groupsDBx: List<Group>,
                navigateToNewAudio: () -> Unit,
                navigateToAudioDetail: () -> Unit,
                search: () -> Unit,
                context: Context,
                contentResolver : ContentResolver,
                modifier : Modifier = Modifier){
    val soundsDB by remember { mutableStateOf(soundsDBx) }
    val groupsDB by remember { mutableStateOf(groupsDBx) }

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
                        .clickable { search() }
                        .size(60.dp)
                        .padding(start = 30.dp, top = 15.dp)
                )
            }
        },
        bottomBar = {
            Column(
                modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)){
                Spacer(modifier = modifier.padding(5.dp))
                Row( modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text = "Developed by Federico Wagner",
                        fontSize = 13.sp, modifier= Modifier.padding(bottom = 5.dp))
                    addAudioBTN( navigateToNewAudio )
                }
                Spacer(modifier = modifier.padding(5.dp))
            }
        }
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            SoundsList(soundsDB,groupsDB,navigateToAudioDetail, context, contentResolver)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun SoundsList(soundsDB: List<Audio>,
               groupsDB: List<Group>,
               navigateToAudioDetail: () -> Unit,
               context: Context,
               contentResolver : ContentResolver) {

    LazyVerticalGrid(cells = GridCells.Fixed(1)){
        items(groupsDB){ group -> GroupView(group, navigateToAudioDetail, context, contentResolver)}
    }
}

@Composable
fun GroupView(  group: Group,
                navigateToAudioDetail: () -> Unit,
                context: Context,
                contentResolver : ContentResolver,
                modifier: Modifier = Modifier){
    val audiosInThisGroup: List<Audio> = DataBase.getAllRecordsInGroup(group)
    var showGroup by remember { mutableStateOf(true) }
    var icon by remember { mutableStateOf(Icons.Filled.KeyboardArrowDown) }

    fun getIcon(): ImageVector {
        return if(showGroup) {
            Icons.Filled.KeyboardArrowDown
        }else{
            Icons.Filled.KeyboardArrowUp
        }
    }

    val showHideGroup = fun(){
        showGroup = !showGroup
        icon = getIcon()
        if(MediaPlayerFW.currentAudiogroupID == group.groupId) {
            MediaPlayerFW.reset()
        }
    }

    Column {
        if( audiosInThisGroup.isNotEmpty() ){
            Row(modifier = modifier.clickable { showHideGroup() }) {
                Spacer(modifier = modifier.padding(10.dp))
                Text(text = group.groupName, color = Color.Gray, fontSize = 23.sp)
                Spacer(modifier = modifier.padding(2.dp))
                Text(text = "(" + audiosInThisGroup.size.toString() + ")",
                    color = Color.Gray, fontSize = 10.sp, modifier = modifier.padding(top = 12.dp))
                Spacer(modifier = modifier.padding(2.dp))
                Icon(icon,"showHideArrow", modifier = modifier.size(35.dp), tint = Color.Gray)
            }
            if(showGroup){
                FlowRow {
                    for (audio in audiosInThisGroup) {
                        SoundCardDB(audio, navigateToAudioDetail, context, contentResolver )
                    }
                }
            }
        }
    }
}

@Composable
fun SoundCardDB( audio: Audio,
                 navigateToAudioDetail: () -> Unit,
                 context: Context,
                 contentResolver : ContentResolver,
              modifier : Modifier = Modifier ) {
    var playPause by remember { mutableStateOf(Icons.Rounded.PlayArrow) }

    val onFinish = fun(){
        playPause = MediaPlayerFW.getIcon()
    }
    val onTap = fun(){
        MediaPlayerFW.tap(context, audio, onFinish)
        playPause = MediaPlayerFW.getIcon()
    }

    //FAVORITE BORDER
    val favNoramlModifier: Modifier = when(audio.favorite){
        true -> {
            Modifier
                .padding(8.dp)
                .height(60.dp)
                .border(1.dp, color = Gold600, shape = RoundedCornerShape(30))
                .clip(RoundedCornerShape(30))
             }
        false -> {
            Modifier
                .padding(8.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(30)) }
    }

    Card(modifier = favNoramlModifier,
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
                            shareSound(context, audio, contentResolver)
                        }
                        .size(28.dp)
                        .padding(end = 5.dp)
                )
            }
        }
    }
}

@Composable
fun addAudioBTN(navigateToNewAudio : () -> Unit , modifier : Modifier = Modifier){
    Box(modifier = modifier
        .clip(RoundedCornerShape(50)),
    ){
        Icon(Icons.Rounded.Add,
            contentDescription = "Show Popup",
            modifier = modifier
                .clickable {
                    navigateToNewAudio()
                }
                .size(50.dp)
                .border(width = 3.dp, color = Green200, shape = CircleShape)
        )
    }
}

//@Composable
//fun DeleteAll(modifier : Modifier = Modifier){
//    Box(modifier = modifier
//        .clip(RoundedCornerShape(50)),
//    ){
//        Icon(Icons.Rounded.Add,
//            contentDescription = "Add Audio",
//            modifier = modifier
//                .clickable {
//                    DataBase.deleteAllRecords()
//                }
//                .size(60.dp)
//                .border(width = 3.dp, color = Purple700, shape = CircleShape)
//        )
//    }
//}
