package com.example.soundsapp.helpers

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.model.Sound


object AudioPlayer
{
    var player: MediaPlayer = MediaPlayer()

    fun play(sound: Sound, context: Context){
        this.player.stop()
        this.player.release()
        this.player = MediaPlayer.create(context, sound.audioFile)
        this.player.start()
    }

    fun play(audio: Audio, context: Context){
        this.player.stop()
        this.player.release()

        println("-------------")
        println("audio.audioURI")
        println(audio.audioURI)
        val audioUri = Uri.parse(audio.audioURI)
        println("Uri.parse(audio.audioURI): ")
        println( audioUri)
        println("-------------")

        this.player = MediaPlayer.create(context, audioUri)
        this.player.start()
    }

}

//class AudioPlayer(
//    var localContext: Context
//){
//    var player: MediaPlayer = MediaPlayer()
//
//    fun play( sound: Sound){
//        this.player.stop()
//        this.player.release()
//        this.player = MediaPlayer.create(this.localContext, sound.audioFile)
//        this.player.start()
//    }
//
//}

//fun getPlayer(context: Context): (Int) -> Unit {
//    var player = MediaPlayer()
//    val play :  (Int) -> Unit = {
//        player.stop()
//        player.release()
//        player = MediaPlayer.create(context, it)
//        player.start()
//    }
//    return play
//}

