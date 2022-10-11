package com.example.soundsapp.model

import android.content.Context
import android.net.Uri
import androidx.room.Room
import com.example.soundsapp.db.AudiosDataBase
import com.example.soundsapp.db.DAO.AudioDAO
import com.example.soundsapp.db.DAO.GroupDAO
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.db.entity.Group
import com.example.soundsapp.ui.addNewAudioScreenObjectStatus


object DataBase {
    lateinit var db: AudiosDataBase
    private lateinit var audioDao : AudioDAO
    private lateinit var groupDao : GroupDAO

    fun createDB(applicationContext: Context){
        val _db = Room.databaseBuilder(
            applicationContext,
            AudiosDataBase::class.java, "database-name"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        this.db = _db
        this.audioDao = _db.audioDAO()
        this.groupDao = _db.groupDAO()
    }

    fun groupCreate(newGroupName : String, editable: Boolean){
        val newGroup = Group(0, newGroupName, editable)
        this.groupDao.insert(newGroup)
    }
    fun groupCreateSAFE(newGroupName : String) : Boolean{
        return if (this.groupDao.getGroupByName(newGroupName) == null){
            //GROUP DOES NOT EXIST
            groupCreate(newGroupName,true)
            true
        }else {
            false
        }
    }
    fun getAllGroups(): List<Group> {
        return this.groupDao.getAll()
    }

    fun deleteGroupByEntitySAFE(group: Group): Boolean {
        return if( group.isEditable ){                     // GROUP IS EDITABLE
            this.groupDao.deleteByEntity(group)
            true
        }else {
            false
        }
    }




    fun insertInDB( audioUserName: String,
                    audioFileName: String,
                    audioURI: Uri?,
                    audioPath: String?,
                    favorite: Boolean,
                    groupId: Long?) {  //TODO remove nullleable value
        val newAudio = Audio(0,
            audioUserName,
            audioFileName,
            audioURI.toString(),
            audioPath.toString(),
            favorite,
            groupId
        )
        this.audioDao.insert(newAudio)
    }

    fun updateAudioInDB(audio: Audio, context: Context) {
        //update in DB
        this.audioDao.updateByEntity(audio)
    }

    fun deleteAudio(audio: Audio, context: Context) {
        //Remove audio from DB
        this.audioDao.deleteByEntity(audio)
        //TODO Revoke app file access
    }

    fun saveAudioinDB(context: Context) {
        //persist in DB
        DataBase.insertInDB(
            addNewAudioScreenObjectStatus.selectedAudioUserName,
            addNewAudioScreenObjectStatus.selectedAudioFileName,
            addNewAudioScreenObjectStatus.selectedAudioUri,
            addNewAudioScreenObjectStatus.selectedAudioPath,
            addNewAudioScreenObjectStatus.favorite,
            addNewAudioScreenObjectStatus.groupId
        )
        //Clean addNewAudioScreenObjectStatus
        addNewAudioScreenObjectStatus.reset()
    }

    fun showAudioRecords() {
        println("audios:  TRY")
        val audios: Map<Audio, Group> = this.audioDao.loadAudiosWithGroupsAndSortedByFavorite()
        audios.forEach {
            println(
                "---------------------\n" +
                        it.key.id+ " - " +
                        it.key.audioUserName + " - " +
                        it.key.audioFileName + "\n" +
                        it.key.audioURI + "\n" +
                        it.key.audioPath + "\n" +
                        it.key.groupId + "\n" +
                        it.key.favorite + "\n" +
                        it.value.groupName +
                        "\n----------------------"
            )
        }
    }

    fun showGroupsRecords() {
        println("grops:")
        val groups: List<Group> = this.groupDao.getAll()
        groups.forEach {
            println(
                "---------------------\n" +
                        it.groupId + " - " +
                        it.groupName +
                        "\n----------------------"
            )
        }
    }

    fun getAllRecordsFull(): Map<Audio, Group> {
        return this.audioDao.loadAudiosWithGroupsAndSortedByFavorite()
    }

    fun getAllRecords(): List<Audio> {
        return this.audioDao.getAllSortedByFavorite()
    }
    fun deleteAllRecords() {
        this.audioDao.deleteAll(this.getAllRecords())
    }
}
