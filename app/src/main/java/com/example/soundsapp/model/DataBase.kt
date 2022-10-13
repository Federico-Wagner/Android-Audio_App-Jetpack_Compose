package com.example.soundsapp.model

import android.content.Context
import android.net.Uri
import androidx.compose.ui.text.capitalize
import androidx.room.Room
import androidx.room.Update
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
    //WARMUP
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

    //GROUPS
    //CREATE
    fun groupCreate(newGroupName : String, editable: Boolean){
        val newGroup = Group(0, newGroupName.replaceFirstChar { it.uppercase() }, editable)
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
    //READ
    fun getAllGroups(): List<Group> {
        return this.groupDao.getAll()
    }
    //UPDATE
    fun updateByEntitySAFE(group: Group, newGroupName: String): Boolean{
        //check if newName does not exist in DB
        return if(group.isEditable && this.groupDao.getGroupByName(newGroupName) == null){
            group.groupName = newGroupName
            this.groupDao.updateByEntity(group)
            true
        }else{
            false
        }
    }
    //DELETE
    fun deleteGroupByEntitySAFE(group: Group): Boolean {
        return if( group.isEditable ){                     // GROUP IS EDITABLE
            var audiosInThisGroup : List<Audio> = this.getAllRecordsInGroup(group)
            fun changeToGeneralGroup(audio: Audio): Audio{
                audio.groupId = 1 //"General" GroupID
                return audio
            }
            audiosInThisGroup = audiosInThisGroup.map { changeToGeneralGroup(it) };

            //migrating current audios int his group to "GENERAL" group
            DataBase.updateAudiosInDBByList(audiosInThisGroup)
            //Deletion of the group
            this.groupDao.deleteByEntity(group)
            true
        }else {
            false
        }
    }


    //AUDIOS
    //CREATE
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
    //READ
    fun getAllRecords(): List<Audio> {
        return this.audioDao.getAllSortedByFavorite()
    }
    fun getAllRecordsInGroup(group: Group): List<Audio> {
        return this.audioDao.getAudiosByGroupIdAndSortedByFavorite(group.groupId.toInt())
    }
    //UPDATE
    fun updateAudioInDB(audio: Audio) {
        //update in DB
        this.audioDao.updateByEntity(audio)
    }
    fun updateAudiosInDBByList(audiosList: List<Audio>) {
        //update in DB
        this.audioDao.updateByEntityList(audiosList)
    }
    //DELETE
    fun deleteAudio(audio: Audio) {
        //Remove audio from DB
        this.audioDao.deleteByEntity(audio)
        //TODO Revoke app file access
    }
    fun deleteAllRecords() {
        this.audioDao.deleteAll(this.getAllRecords())
    }


    //AUDIO & GROUPS
    fun getAllRecordsFull(): Map<Audio, Group> {
        return this.audioDao.loadAudiosWithGroupsAndSortedByFavorite()
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
}
