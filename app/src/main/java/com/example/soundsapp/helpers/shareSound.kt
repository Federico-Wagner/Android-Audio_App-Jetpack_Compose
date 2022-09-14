package com.example.soundsapp.helpers

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.soundsapp.R
import com.example.soundsapp.model.Sound


fun shareSound(intentContext: Context, sound : Sound) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            intentContext.getString(sound.audioFile,sound.stringResourceId)
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    try {
        ContextCompat.startActivity(intentContext, shareIntent, null)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            intentContext,
            intentContext.getString(R.string.send_error),
            Toast.LENGTH_LONG
        ).show()
    }
}