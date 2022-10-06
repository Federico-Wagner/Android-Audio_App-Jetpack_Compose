package com.example.soundsapp.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.soundsapp.ui.theme.Black050
import com.example.soundsapp.ui.theme.Black900


@Composable
fun AddAudioPopup(audioSearchBTN: () -> Unit,
                  saveBTN: (String) -> Unit,
                  goBackBTN: () -> Unit,
                  showHidePopupBTN: () -> Unit,
                  context: Context,
                  modifier: Modifier = Modifier){

    Popup(
        alignment = Alignment.Center,
        properties = PopupProperties()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Black050)
                .clickable { showHidePopupBTN() },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = modifier
                    .height(360.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .background(Black900, RoundedCornerShape(10.dp))
                    .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
                    .clickable(enabled = false) {  }

            ) {
                AddAudioScreen(audioSearchBTN,
                    saveBTN,
                    goBackBTN,
                    context)
            }
        }
    }
}





