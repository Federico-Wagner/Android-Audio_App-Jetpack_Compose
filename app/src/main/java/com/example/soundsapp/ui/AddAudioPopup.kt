package com.example.soundsapp.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties


// on below line we are creating a pop up window dialog method
@Composable
fun PopupWindowDialog(audioSearchBTN: () -> Unit,
                      saveBTN: (String) -> Unit,
                      goBackBTN: () -> Unit,
                      context: Context,
                      modifier: Modifier = Modifier
) {
    val openDialog = remember { mutableStateOf(false) }
    val buttonTitle = remember {
        mutableStateOf("Show Pop Up")
    }
    Column(modifier = Modifier
        .fillMaxSize()
//        .height(410.dp)
        .padding(horizontal = 20.dp , vertical = 150.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top){
        Box {
            val popupWidth = 350.dp
            val popupHeight = 300.dp
            if (openDialog.value) {
                buttonTitle.value = "Hide Pop Up"
                Popup(
                    alignment = Alignment.TopCenter,
                    properties = PopupProperties()
                ) {
                    Box(
                        Modifier
                            .size(popupWidth, popupHeight)
                            .padding(top = 5.dp)
                            .background(Color.Gray, RoundedCornerShape(10.dp))
                            .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AddAudioScreen(audioSearchBTN,
                                saveBTN,
                                goBackBTN,
                                context)
                        }
                        }
                    }
                }
            }
        }


    Column( modifier = Modifier
//            .fillMaxSize()
        .padding(horizontal = 20.dp , vertical = 50.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button( modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            onClick = {
                openDialog.value = !openDialog.value
                if (!openDialog.value) {
                    buttonTitle.value = "Show Pop Up"
                }
            }
        ) {
            Text(text = buttonTitle.value, modifier = Modifier.padding(3.dp))
        }

    }
}