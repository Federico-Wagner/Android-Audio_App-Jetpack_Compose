package com.example.soundsapp.helpers

import android.app.Activity
import android.content.*
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.soundsapp.AUDIO_FILES_FOLDER_NAME
import com.example.soundsapp.BuildConfig
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URI
import java.util.concurrent.TimeUnit


fun shareSound(context: Context, audio: Audio, r : ContentResolver) {
    try {
        println(Uri.parse(audio.audioURI).path!!)
        println(audio.audioPath)

        val file2 = File(context.filesDir, AUDIO_FILES_FOLDER_NAME + "/" + audio.audioFileName)

        if (file2.exists()) {
            val uri =
                FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file2)
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setType("audio/*")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            val bundle = Bundle()
            startActivity(context, intent, bundle)
        }

        ToastHelper.sendToastMesage(audio.audioFileName + " Shared", context)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        ToastHelper.sendToastMesage("Sharing Error: " + audio.audioFileName, context)
    }
}
