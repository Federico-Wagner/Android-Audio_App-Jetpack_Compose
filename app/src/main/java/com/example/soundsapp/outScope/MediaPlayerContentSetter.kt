package com.example.soundsapp.outScope

//import android.annotation.SuppressLint
//import android.content.Context
//import android.database.Cursor
//import android.media.MediaPlayer
//import android.net.Uri
//import android.os.Build
//import android.provider.MediaStore
//import java.io.File
//import java.io.FileInputStream


//class MediaPlayerContentSetter {
//}
//
//
//@Throws(Exception::class)
//fun setMediaPlayerDataSource(
//    context: Context,
//    mp: MediaPlayer, fileInfo: String
//) {
//    var fileInfo = fileInfo
//    if (fileInfo.startsWith("content://")) {
//        try {
//            val uri: Uri = Uri.parse(fileInfo)
//            fileInfo = getRingtonePathFromContentUri(context, uri)
//        } catch (e: Exception) {
//        }
//    }
//    try {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) try {
//            setMediaPlayerDataSourcePreHoneyComb(context, mp, fileInfo)
//        } catch (e: Exception) {
//            setMediaPlayerDataSourcePostHoneyComb(context, mp, fileInfo)
//        } else setMediaPlayerDataSourcePostHoneyComb(context, mp, fileInfo)
//    } catch (e: Exception) {
//        try {
//            setMediaPlayerDataSourceUsingFileDescriptor(
//                context, mp,
//                fileInfo
//            )
//        } catch (ee: Exception) {
//            val uri = getRingtoneUriFromPath(context, fileInfo)
//            mp.reset()
//            mp.setDataSource(uri)
//        }
//    }
//}
//
//@Throws(Exception::class)
//private fun setMediaPlayerDataSourcePreHoneyComb(
//    context: Context,
//    mp: MediaPlayer, fileInfo: String
//) {
//    mp.reset()
//    mp.setDataSource(fileInfo)
//}
//
//@Throws(Exception::class)
//private fun setMediaPlayerDataSourcePostHoneyComb(
//    context: Context,
//    mp: MediaPlayer, fileInfo: String
//) {
//    mp.reset()
//    mp.setDataSource(context, Uri.parse(Uri.encode(fileInfo)))
//}
//
//@Throws(Exception::class)
//private fun setMediaPlayerDataSourceUsingFileDescriptor(
//    context: Context, mp: MediaPlayer, fileInfo: String
//) {
//    val file = File(fileInfo)
//    val inputStream = FileInputStream(file)
//    mp.reset()
//    mp.setDataSource(inputStream.getFD())
//    inputStream.close()
//}
//
//@SuppressLint("Range")
//private fun getRingtoneUriFromPath(context: Context, path: String): String {
//    val ringtonesUri: Uri? = MediaStore.Audio.Media.getContentUriForPath(path)
//    val ringtoneCursor: Cursor? = ringtonesUri?.let {
//        context.contentResolver.query(
//            it, null,
//        MediaStore.Audio.Media.DATA + "='" + path + "'", null, null
//    )
//    }
//    if(ringtoneCursor != null){
//
//        ringtoneCursor.moveToFirst()
//        val id: Long = ringtoneCursor.getLong(
//            ringtoneCursor
//                .getColumnIndex(MediaStore.Audio.Media._ID)
//        )
//        ringtoneCursor.close()
//        return if (!ringtonesUri.toString().endsWith(id.toString())) {
//            ringtonesUri.toString() + "/" + id
//        } else ringtonesUri.toString()
//    }
//    return "EMPTY"
//}
//
//fun getRingtonePathFromContentUri(
//    context: Context,
//    contentUri: Uri?
//): String {
//    val proj = arrayOf(MediaStore.Audio.Media.DATA)
//    if(contentUri != null){
//        val ringtoneCursor: Cursor? = context.contentResolver.query(
//            contentUri,
//            proj, null, null, null
//        )
//        ringtoneCursor?.moveToFirst()
//        val path: String = ringtoneCursor!!.getString(
//            ringtoneCursor
//                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
//        )
//        ringtoneCursor.close()
//        return path
//    }
//
//    return "EMPTYYYYY"
//}