package com.example.soundsapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Sound(
    val stringResourceId: String,
    val audioFile: Int
)