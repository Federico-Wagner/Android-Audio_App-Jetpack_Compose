package com.example.soundsapp.helpers

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import java.util.logging.Level
import java.util.logging.Logger

object MediaPlayerFW {
    var logger = Logger.getLogger("MediaPlayerFW-Loger")
    var player: MediaPlayer = MediaPlayer()
    var state: PlayerState = PlayerState.STOP

    enum class PlayerState {
        STOP, PLAY, PAUSE
    }

    fun tap(context: Context, audioUri : Uri?){
        when (this.state) {
            PlayerState.STOP -> { this.setAndPlay(context, audioUri) }
            PlayerState.PLAY -> { this.pause() }
            PlayerState.PAUSE -> { this.play() }
            else -> { logger.log(Level.WARNING,"MediaPlayerFW - tap reached else output")}
        }
//        this.player.isPlaying
    }

    fun setAndPlay(context: Context, audioUri : Uri?){
        logger.log(Level.INFO,"MediaPlayerFW - Playing: " + audioUri.toString())
        if(audioUri != null) {
            this.state = PlayerState.PLAY
            this.player = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(context, audioUri)
                prepare()
                start()
            }
        }
    }
    fun play(){
        this.player.start()
        this.state = PlayerState.PLAY
    }
    fun pause(){
        this.player.pause()
        this.state = PlayerState.PAUSE

    }
    fun stop(){
        this.player.stop()
        this.state = PlayerState.STOP
    }
    fun reset(){
        this.player.reset()
        this.state = PlayerState.STOP
    }
}
