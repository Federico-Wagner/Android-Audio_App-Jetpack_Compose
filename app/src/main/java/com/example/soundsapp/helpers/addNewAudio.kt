package com.example.soundsapp.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.MainThread
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.soundsapp.MainActivity
import java.io.File

public fun addNewAudio(intentContext: Context,activity: Activity) {
    //TODO()
    Log.d("helpers.addNewAudio","addNewAudio function executed")

    var files: Array<String> = intentContext.fileList()
    println("lista:--------------")
    files.forEach { println(it.toString()) }



    val pickFromAudioGallery = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
    pickFromAudioGallery.type = "audio/*"


    var bundle = Bundle()
    startActivityForResult(activity, pickFromAudioGallery, 1, bundle)


//    val file = File(intentContext.filesDir)
}