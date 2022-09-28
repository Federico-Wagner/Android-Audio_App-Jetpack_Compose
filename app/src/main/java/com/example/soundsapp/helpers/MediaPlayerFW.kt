package com.example.soundsapp.helpers

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.soundsapp.db.entity.Audio
import java.util.logging.Level
import java.util.logging.Logger


object MediaPlayerFW {
    val logger = Logger.getLogger("MediaPlayerFW-OBJ")
    var player: MediaPlayer = MediaPlayer()
    var state: PlayerState = PlayerState.STOP
    var currentAudioUri: String? = null

    enum class PlayerState {
        STOP, PLAY, PAUSE
    }

    fun tap(context: Context, audio : Audio, onFinish: () -> Unit){
        if(audio.audioURI == this.currentAudioUri ){
            //TAPING ON THE SAME AUDIO BTN FOR PLAY PAUSE
            when (this.state) {
                PlayerState.STOP -> { this.play() }
                PlayerState.PLAY -> { this.pause() }
                PlayerState.PAUSE -> { this.play() }
            }
        }else{
            //TAPPED ON ANOTHER AUDIO
            this.currentAudioUri = audio.audioURI
            this.setAndPlay(context, audio, onFinish)
        }
    }

    fun setAndPlay(context: Context, audio: Audio, onFinish: () -> Unit){
        val audioUri = Uri.parse(audio.audioURI)
        if(audioUri != null) {
            try{
                if(this.player.isPlaying) { MediaPlayerFW.stop() }
                this.player = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setOnCompletionListener {
                        MediaPlayerFW.stopPrivate()
                        onFinish()
                    }
                    setDataSource(context, audioUri)
                    prepare()
                    start()
                }
                this.state = PlayerState.PLAY
            }catch (e: Exception){
                logger.log(Level.SEVERE,e.toString())
                println(e)
            }
        }
    }
    //PRIVATE METHODS
    private fun stopPrivate(){
        this.player.stop()
        this.player.prepare()
        this.state = PlayerState.STOP
    }

    //PUBLIC CONTROLS METHODS
    fun play(){
        this.player.start()
        this.state = PlayerState.PLAY
    }
    fun pause(){
        this.player.pause()
        this.state = PlayerState.PAUSE
    }
     fun stop(){
         if(this.player.isPlaying) {
             this.player.stop()
             this.player.prepare()
             this.state = PlayerState.STOP
         }
    }
    fun reset(){
        this.player.reset()
        this.state = PlayerState.STOP
    }

    //UI
    fun getIcon(): ImageVector {
        return if(MediaPlayerFW.player.isPlaying){
            Icons.Rounded.Pause
        }else{
            Icons.Rounded.PlayArrow
        }
    }
}
