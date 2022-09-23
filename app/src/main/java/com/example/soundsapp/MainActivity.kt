package com.example.soundsapp

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.media.AudioAttributes
import android.media.MediaDataSource
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler
import com.example.soundsapp.data.DataSource
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.SoundApp
import com.example.soundsapp.ui.theme.SoundsAppTheme
import java.io.FileDescriptor
import javax.xml.transform.URIResolver
import kotlin.concurrent.timer


class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
//    lateinit var dataBaseRows : List<Audio>

var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
        val audioName = "audio name future custom3"
//        val mediaPlayer = MediaPlayer()
//        println("result")
//        println(result)
//        println(result.data?.data)
//        val uri = Uri.parse(result.data?.toUri(Intent.URI_INTENT_SCHEME))
        val myUri1: Uri? = result.data?.data?.normalizeScheme()
//        val contentUri: Uri =
//            ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 39 )
//        println("contentUri")
//        println(contentUri)
        val audioUri: Uri? = myUri1 // initialize Uri here

        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            if (audioUri != null) {
                setDataSource(applicationContext, audioUri)
            }
            prepare()
            start()
        }


//        if (myUri != null) {
////            mediaPlayer.setDataSource(applicationContext, myUri)
////            mediaPlayer.prepare()
////            mediaPlayer.start()
//
//
//            val mediaPlayer1 = MediaPlayer()
//            mediaPlayer1.setDataSource( myUri.path)
//            mediaPlayer1.prepareAsync()
//
////            val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.awantia)
//
//            mediaPlayer1.start()
//        }
//        val mp = MediaPlayer()
//        mp.setDataSource(myUri?.encodedPath);
//        mp.start()



//        println("trying to play: ")
//        println(myUri)
//        println(myUri?.describeContents())
//        val player = MediaPlayer.create(applicationContext, myUri)
//        player.start()
//
//        val mediaPlayer = MediaPlayer().apply {
//            setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .build()
//            )
//            if (myUri != null) {
//                println("seted?")
//                setDataSource(applicationContext, myUri)
//            }
//            prepare()
//            start()
//        }
//        mediaPlayer.start()

//        var fileSize: String = "fede"
//
//        val cursor: Cursor? =
//            myUri?.let { contentResolver.query(it, null, null, null, null, null) }
//        try {
//            if (cursor != null && cursor.moveToFirst()) {
//
//                // get file size
//                val sizeIndex: Int = cursor.getColumnIndex(OpenableColumns.SIZE)
//                val nameIndex: Int = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//
//                if (!cursor.isNull(sizeIndex)) {
//                    println("cursor.notificationUri")
//                    println(cursor.getString(sizeIndex))
//                }
//
//                if (!cursor.isNull(nameIndex)) {
//                    println("nameIndex")
//                    println(cursor.getString(nameIndex))
//                }
////                val dataIndex: Int = cursor.getColumnIndex(OpenableColum)
////                cursor.getColumnIndex()
//            }
//        } finally {
//            cursor?.close()
//        }
//        println("uri path: ")
//        println(myUri?.path)



//        DataBase.insertInDB(result.data!!,audioName,applicationContext)
//
//        println("RECOGNIZE ME")
//        DataBase.showRecords()
    }
}
    val opn = fun() {
        val intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_GET_CONTENT

        resultLauncher.launch(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate Called")
        super.onCreate(savedInstanceState)

        DataBase.createDB(applicationContext)
        DataBase.showRecords()

        var dataBaseRows : List<Audio> = DataBase.getAllRecords()
        setContent {
            SoundsAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    SoundApp(DataSource.sounds, this@MainActivity, opn, dataBaseRows, addAudioBTN)

                }
            }
        }
    }

    private val addAudioBTN = fun() {
        val intent = Intent(this, AddAudioActivity::class.java)
        startActivity(intent)
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }
    override fun onResume() {
        super.onResume()
//        this.dataBaseRows = DataBase.getAllRecords()
        Log.d(TAG, "onResume Called")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }
}
