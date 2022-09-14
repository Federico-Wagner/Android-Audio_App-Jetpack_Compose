package com.example.soundsapp.helpers

import android.content.Context
import android.media.MediaPlayer

fun getPlayer(context: Context): (Int) -> Unit {
    var player = MediaPlayer()
    val play :  (Int) -> Unit = {
        player.stop()
        player.release()
        player = MediaPlayer.create(context, it)
        player.start()
    }
    return play
}
