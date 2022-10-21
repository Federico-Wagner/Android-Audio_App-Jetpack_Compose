package com.example.soundsapp.helpers

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toUri
import com.example.soundsapp.AUDIO_FILES_FOLDER_NAME
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.logging.Logger

object FileManger {

    fun onSave(context : Context ,audioUri : Uri, fileName : String ,r : ContentResolver ): Boolean{
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
            return true
        }catch (e:Exception){
            println(e)
            return false
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

    fun getFileName(audioUri: Uri?, r: ContentResolver): String?{
        //Retrieving audio fileName
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
        )
        val cursor: Cursor? =
            r.query(audioUri!!, projection, null, null, null, null)
        try {
            if ((cursor != null) && cursor.moveToFirst()) {
                val nameIndex: Int = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (!cursor.isNull(nameIndex)) {
                    return cursor.getString(nameIndex)
                }
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun deleteFile(fileUriString : String) : Boolean{
        return try{
            val fileUri = Uri.parse(fileUriString)
            val file = File(fileUri.path!!)
            if (file.isFile){
                file.delete()
                Log.d("FileManger.deleteFile", "deleted file: $fileUriString")
                true
            }else{
                Log.d("FileManger.deleteFile", "Could NOT delete file: $fileUriString")
                false
            }
        }catch (e : Exception){
            Log.d("FileManger.deleteFile", "error deleting audio: $fileUriString")
            false
        }
    }

    fun deleteAllFiles(context: Context){
        // FOR DEBUG
        val contect = context.filesDir.listFiles()!![0].listFiles()
        for(audioFile in contect!!){
            println( audioFile.path.toUri() )
            FileManger.deleteFile(audioFile.path.toUri().toString())
        }
    }

}