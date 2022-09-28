package com.example.soundsapp.helpers
//
//import android.content.ContentResolver
//import android.content.ContentValues
//import android.content.Context
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.MediaStore
//import androidx.annotation.RequiresApi
//
//object MediaHelper {
//    var resolver: ContentResolver? = null
//    var audioCollection: Uri? = null
//
//    fun mediaHelperIsActive(): Boolean{
//        return this.resolver != null && this.audioCollection != null
//    }
//
//    fun createMediaHelper(context: Context){
//        //application context set
//        this.resolver = context.contentResolver
//        this.audioCollection =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                MediaStore.Audio.Media.getContentUri(
//                    MediaStore.VOLUME_EXTERNAL_PRIMARY
//                )
//            } else {
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//            }
//    }
//
//
//    fun addNewAudio(audioName: String, selectedFileUri: Uri): Uri? {
//        if(mediaHelperIsActive()){
//            // Publish a new song.
//            val newSongDetails = ContentValues().apply {
//                put(MediaStore.Audio.Media.DISPLAY_NAME, audioName)
//                put(MediaStore.Audio.Media.DATA, selectedFileUri.toString())
//            }
//            // Keeps a handle to the new song's URI in case we need to modify it later.
//            val myFavoriteSongUri = this.resolver
//                ?.insert(this.audioCollection!!, newSongDetails)
//            return myFavoriteSongUri
//        }
//        return null
//    }
//
//}
