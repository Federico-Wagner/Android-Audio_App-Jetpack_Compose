package com.example.soundsapp.helpers

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.example.soundsapp.AUDIO_FILES_FOLDER_NAME
import com.example.soundsapp.R
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object FileManger {

    fun onSave(context : Context ,audioUri : Uri, fileName : String ,r : ContentResolver ){
        try {
            val dir = mkdir(context, AUDIO_FILES_FOLDER_NAME)
            val copyToUri = File(dir, fileName).toUri()
            copyFile(r, audioUri, copyToUri)

            val newAudio = Audio(
                0,
                addNewAudioScreenObjectStatus.selectedAudioUserName,
                addNewAudioScreenObjectStatus.selectedAudioFileName,
                copyToUri.toString(),
                copyToUri.path!!,
                addNewAudioScreenObjectStatus.favorite,
                addNewAudioScreenObjectStatus.groupId
            )
            DataBase.saveAudioinDB(newAudio)
            //Reset Object
            addNewAudioScreenObjectStatus.reset()
            //Give Feedback to user
            ToastHelper.sendToastMesage( R.string.audioSaved.toString(), context) //TODO check

        }catch (e:Exception){
            println(e)
            //Give Feedback to user
            ToastHelper.sendToastMesage(R.string.audioError.toString(), context)  //TODO check
        }
    }

    fun copyFile(r: ContentResolver, from: Uri, to: Uri ) {
        val iStream: InputStream = r.openInputStream(from)!!
        val oStream: OutputStream = r.openOutputStream(to)!!

        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0

        while (iStream.read(buffer).also { len = it } != -1) {
            oStream.write(buffer, 0, len)
        }
        oStream.flush()
        oStream.close()
        iStream.close()
    }

    fun mkdir(context: Context, dirName: String): File {
        val dir = File(context.filesDir, dirName)
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir
    }
}