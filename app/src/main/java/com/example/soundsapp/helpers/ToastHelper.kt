package com.example.soundsapp.helpers

import android.content.Context
import android.widget.Toast

object ToastHelper {

    fun sendToastMesage(message: String, applicationContext: Context) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, message, duration)
        toast.show()
    }
}