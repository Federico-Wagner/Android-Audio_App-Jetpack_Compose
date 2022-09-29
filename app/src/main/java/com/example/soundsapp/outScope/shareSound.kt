package com.example.soundsapp.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.ContextCompat.startActivity
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Audio


fun shareSound(context: Context, audio: Audio) {



    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "audio/*"

        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra(Intent.EXTRA_STREAM, Uri.parse(audio.audioURI))
    }

    val bundle = Bundle()

    val title = R.string.share


    startActivity(context, sendIntent, bundle)
}