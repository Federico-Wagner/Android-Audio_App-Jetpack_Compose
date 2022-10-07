package com.example.soundsapp

//import android.app.Activity
//import android.content.Intent
//import android.database.Cursor
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.provider.OpenableColumns
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.material.Surface
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import com.example.soundsapp.helpers.MediaPlayerFW
//import com.example.soundsapp.model.DataBase.saveAudioinDB
//import com.example.soundsapp.ui.AddAudioScreen
//import com.example.soundsapp.ui.addNewAudioScreenObjectStatus
//import com.example.soundsapp.ui.theme.SoundsAppTheme
//import java.util.logging.Logger
//
//
//class AddAudioActivity : ComponentActivity(){ //AppCompatActivity() { //TODO search difference
//    var logger = Logger.getLogger("AddAudioActivity-Loger")
//    val audioSearchBTN = fun() {
//        val intent = Intent()
//        intent.type = "audio/*"
////        intent.action = Intent.ACTION_GET_CONTENT     //TEMPORAL PERMISSION
//        intent.action = Intent.ACTION_OPEN_DOCUMENT     //PERMANENT PERMISSION
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        resultLauncher.launch(intent)
//    }
//    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val audioUri: Uri? = result.data?.data?.normalizeScheme()
//            // Save Uri & PATH of selected AUDIO FILE
//            addNewAudioScreenObjectStatus.selectedAudioUri = audioUri
//            addNewAudioScreenObjectStatus.selectedAudioPath = result.data?.data?.path
//
//            //Retrieving audio fileName
//            val projection = arrayOf(
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.DISPLAY_NAME,
//                MediaStore.Audio.Media.DURATION,
//                MediaStore.Audio.Media.SIZE,
//            )
//            val cursor: Cursor? =
//                contentResolver.query(audioUri!!, projection, null, null, null, null)
//            try {
//                if ((cursor != null) && cursor.moveToFirst()) {
//                    val nameIndex: Int = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//                    if (!cursor.isNull(nameIndex)) {
//                        addNewAudioScreenObjectStatus.selectedAudioFileName = cursor.getString(nameIndex)
//                    }
//                }
//            } finally {
//                cursor?.close()
//            }
//        }
//    }
//
//    val saveBTN = fun(audioName : String) {
//        saveAudioinDB(applicationContext)
//        addNewAudioScreenObjectStatus.reset()
//        MediaPlayerFW.reset()
//        goBackBTN()
//    }
//    val goBackBTN = fun() {  //TODO FIX NAVIGATION
//        //Back to Home
//        val startIntent = Intent(applicationContext, MainActivity::class.java)
//        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        applicationContext.startActivity(startIntent)
//        MediaPlayerFW.reset()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        addNewAudioScreenObjectStatus.reset()
//        MediaPlayerFW.reset()
//        setContent {
//            SoundsAppTheme(darkTheme = true) {
//                Surface(
//                    modifier = Modifier
//                        .height(300.dp)
//                        .fillMaxWidth(),
//                    color = Color.Transparent
//                ) {
//                    AddAudioScreen(audioSearchBTN,
//                        saveBTN,
//                        goBackBTN,
//                        applicationContext)
//                }
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        addNewAudioScreenObjectStatus.reset()
//        MediaPlayerFW.reset()
//    }
//
//    override fun onActivityReenter(resultCode: Int, data: Intent?) {
//        super.onActivityReenter(resultCode, data)
//        addNewAudioScreenObjectStatus
//        MediaPlayerFW.reset()
//    }
//}
