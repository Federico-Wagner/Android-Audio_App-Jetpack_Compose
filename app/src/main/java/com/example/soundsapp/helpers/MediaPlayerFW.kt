package com.example.soundsapp.helpers

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import com.example.soundsapp.db.entity.Audio
import java.util.logging.Level
import java.util.logging.Logger

object MediaPlayerFW {
    var logger = Logger.getLogger("MediaPlayerFW-Loger")
    var player: MediaPlayer = MediaPlayer()
    var state: PlayerState = PlayerState.STOP
    var currentAudioId: Long = 0



    enum class PlayerState {
        STOP, PLAY, PAUSE
    }

    fun tap(context: Context, audio : Audio){
        logger.log(Level.FINE,"MediaPlayerFW - STATE:" + this.state)
        logger.log(Level.FINE,"MediaPlayerFW AUDIO:-------------------------------------------------------------------------" )
        println(audio)
        logger.log(Level.FINE,"MediaPlayerFW AUDIO:-------------------------------------------------------------------------" )
        if(audio.id == this.currentAudioId){
            //TAPING ON THE SAME AUDIO BTN FOR PLAY PAUSE
            when (this.state) {
                PlayerState.STOP -> { this.setAndPlay(context, audio) }
                PlayerState.PLAY -> { this.pause() }
                PlayerState.PAUSE -> { this.play() }
                else -> { logger.log(Level.WARNING,"MediaPlayerFW - tap reached else output")}
            }
        }else{
            //TAPPED ON ANOTHER AUDIO
            this.currentAudioId = audio.id
            this.setAndPlay(context, audio)
        }


//        when (this.state) {
//            PlayerState.STOP -> { this.setAndPlay(context, audio) }
//            PlayerState.PLAY -> { this.pause() }
//            PlayerState.PAUSE -> { this.play() }
//            else -> { logger.log(Level.WARNING,"MediaPlayerFW - tap reached else output")}
//        }
    }

    fun setAndPlay(context: Context, audio: Audio){
        val audioUri = Uri.parse(audio.audioURI)

        logger.log(Level.INFO,"MediaPlayerFW - Playing: " + audioUri.toString())
        if(audioUri != null) {
            try{
                if(this.player.isPlaying) { this.player.stop() }
                this.state = PlayerState.PLAY
                this.player = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(context, audioUri)
                    setOnCompletionListener { MediaPlayerFW.stop() }
                    prepareAsync()
//                    prepare()
                    setOnPreparedListener { start() }
//                    start()
                }
            }catch (e: Exception){
                logger.log(Level.SEVERE,e.toString())
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
