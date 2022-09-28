package com.example.soundsapp.helpers

import android.content.Context
import android.content.Intent


fun shareSound(intentContext: Context) {
//    val intent = Intent(Intent.ACTION_SEND)

//    val sendIntent = Intent().apply {
//        action = Intent.ACTION_SEND
//        putExtra(
//            Intent.EXTRA_TEXT,
//            intentContext.getString(sound.audioFile,sound.stringResourceId)
//        )
//        type = "audio/*"
//    }

//    val intent = Intent().apply {
//    action = Intent.ACTION_SEND
//    putExtra(
//        Intent.EXTRA_STREAM,
//        sound.audioFile
//    )
//    type = "audio/*"
//    }
//    intent.clipData = ClipData(R.raw.buenardo)

//    val intent = Intent(Intent.ACTION_SEND)
//    intent.putExtra(
//        Intent.EXTRA_STREAM,
//        Uri.parse("file://" + Environment.DIRECTORY_MUSIC + "/sound.mp3")
//    sound.audioFile
//    )
//    intent.type = "audio/*"


//    val shareIntent = Intent.createChooser(sendIntent, "Sharing audio: " + sound.stringResourceId)
//    try {
//        ContextCompat.startActivity(intentContext, shareIntent, null)
//        ContextCompat.startActivity(intentContext, intent, null)
//    } catch (e: ActivityNotFoundException) {
//        Toast.makeText(
//            intentContext,
//            intentContext.getString(R.string.send_error),
//            Toast.LENGTH_LONG
//        ).show()
//    }
}