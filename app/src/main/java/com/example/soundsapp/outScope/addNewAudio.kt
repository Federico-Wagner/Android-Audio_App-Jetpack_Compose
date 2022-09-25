package com.example.soundsapp.outScope

//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.provider.MediaStore
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.core.app.ActivityCompat.startActivity
//import androidx.core.app.ActivityCompat.startActivityForResult
//import com.example.soundsapp.MainActivity
//
//
//fun addNewAudio(intentContext: Context,activity: Activity) {
//    Log.d("helpers.addNewAudio","addNewAudio function executed")
//
////    var bundle = Bundle()
////    val intent = Intent()
////    intent.type = "audio/*"
////    intent.action = Intent.ACTION_GET_CONTENT
//
//
//    val intent = Intent()
//    intent.type = "audio/*"
//    intent.action = Intent.ACTION_GET_CONTENT
////        val intent = Intent(this, SecondActivity::class.java)
////    activityResultLauncher.launch(intent)
//
////    activityResultLauncher.launch(intent)
//
////    startActivityForResult(activity, intent_upload, 1, bundle)
//
//
////    registerForActivityResult
////    val getResult = ActivityResultContracts.GetContent().parseResult(Activity.RESULT_OK, intent_upload)
////
////
////    println("##########   ###########")
////    println(getResult.toString())
////    println("##########   ###########")
//
////  first of all open gallery through intent -
////    fun openGalleryForAudio() {
////        var bundle = Bundle()
////        val audioIntent : Intent = Intent(
////        Intent.ACTION_PICK,
////        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
////        startActivityForResult(activity, Intent.createChooser(audioIntent, "Select Audio"),1, bundle)
////    }
//
//
//
////    val pickFromAudioGallery = Intent(Intent.ACTION_PICK)
////    pickFromAudioGallery.type = "audio/*"
////    var bundle = Bundle()
////    var result = startActivityForResult(activity, pickFromAudioGallery, 1, bundle)
////
////    println(pickFromAudioGallery.data?.encodedPath.toString())
//
//
//
////    val intent = Intent(Intent.ACTION_GET_CONTENT)
////    intent.type = "file/*"
////
////    startActivityForResult(activity, intent, PICKFILE_REQUEST_CODE)
////
////    val pickFromGallery = Intent(Intent.ACTION_GET_CONTENT)
//////    pickFromGallery.type = "/image"
////    pickFromGallery.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
////
////    var result2 = startActivityForResult(activity, pickFromGallery, 1, bundle)
////
////    result2.
////
////
////    println("##########   ###########")
////    println(result.toString())
////    println("##########   ###########")
////
////
////    val uri =  Uri.parse("http://stackoverflow.com")
////    DataBase.insertInDB(uri, "audioNameTRY")
//
//
//
//
////    DataBase.showRecords()
//
////    MediaHelper.run(intentContext)
//
////    val file = File(intentContext.filesDir)
//}