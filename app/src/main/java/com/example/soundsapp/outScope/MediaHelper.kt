//package com.example.soundsapp.outScope
//
//import android.content.ContentUris
//import android.content.Context
//import android.net.Uri
//import android.os.Build
//import android.provider.MediaStore
//import androidx.core.net.toUri
//import com.example.soundsapp.AUDIO_FILES_FOLDER_NAME
//import com.example.soundsapp.model.DataBase
//import java.io.File
//import java.util.concurrent.TimeUnit
//
//object MediaHelper {
//    fun run(applicationContext : Context ){
//
//        data class Audios(val uri: Uri,
//                         val name: String,
//                         val duration: Int,
//                         val size: Int,
//                         val date: String,
//        )
//        val audioList = mutableListOf<Audios>()
//
//        val collection =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                MediaStore.Audio.Media.getContentUri(
//                    MediaStore.VOLUME_EXTERNAL
////                    MediaStore.VOLUME_INTERNAL
//                )
//            } else {
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
////                MediaStore.Audio.Media.INTERNAL_CONTENT_URI
//            }
//
//
//        println("############# collection.encodedPath.toString() ##############")
//        println( collection.encodedPath.toString() )
//        println("--------------------------------------------------------------")
//
//        val projection = arrayOf(
//            MediaStore.Audio.Media._ID,
//            MediaStore.Audio.Media.DISPLAY_NAME,
//            MediaStore.Audio.Media.DURATION,
//            MediaStore.Audio.Media.SIZE,
//            MediaStore.Audio.Media.DATE_ADDED
//        )
//
//// Show only videos that are at least 5 minutes in duration.
//        val selection = null //"${MediaStore.Audio.Media.DURATION} >= ?"
//        val selectionArgs = null //arrayOf(
////            TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES).toString()
////        )
//
//// Display audios by date added.
//        val sortOrder = null //"${MediaStore.Video.Media.DATE_ADDED} ASC"
//
//        val query = applicationContext.contentResolver.query(
//            collection,
//            projection,
//            selection,
//            selectionArgs,
//            sortOrder
//        )
//
//        query?.use { cursor ->
//            // Cache column indices.
//            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
//            val nameColumn =
//                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
//            val durationColumn =
//                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
//            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
//            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
//
//            while (cursor.moveToNext()) {
//                // Get values of columns for a given video.
//                val id = cursor.getLong(idColumn)
//                val name = cursor.getString(nameColumn)
//                val duration = cursor.getInt(durationColumn)
//                val size = cursor.getInt(sizeColumn)
//                val date = cursor.getString(dateColumn)
//
//                val contentUri: Uri = ContentUris.withAppendedId(
//                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                    id
//                )
//                // Stores column values and the contentUri in a local object
//                // that represents the media file.
//                audioList += Audios(contentUri, name, duration, size, date)
//            }
//        }
//        println("######## audioList #########")
//        println(audioList.size)
//        println(audioList)
//        println("######## audioList #########")
//    }
//}
//
//
//////        val projection = arrayOf(media-database-columns-to-retrieve)
//////        val selection = sql-where-clause-with-placeholder-variables
//////        val selectionArgs = values-of-placeholder-variables
//////        val sortOrder = sql-order-by-clause
//////
//////        applicationContext.contentResolver.query(
//////            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//////            projection,
//////            selection,
//////            selectionArgs,
//////            sortOrder
//////        )?.use { cursor ->
//////            while (cursor.moveToNext()) {
//////                // Use an ID column from the projection to get
//////                // a URI representing the media item itself.
//////            }
//////        }