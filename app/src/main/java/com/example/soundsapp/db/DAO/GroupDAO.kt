package com.example.soundsapp.db.DAO

import androidx.room.*
import com.example.soundsapp.db.entity.Group


@Dao
abstract class GroupDAO {
    //CREATE
    @Insert
    abstract fun getNextId(entity: Group): Long

    //READ
    @Query("SELECT * FROM groups")
    abstract fun getAll(): List<Group>
    @Query("SELECT * FROM groups WHERE groups.group_name = :groupName")
    abstract fun getGroupByName(groupName : String): Group?

    fun insert(entity: Group): Group {
        entity.groupId = getNextId(entity) //id re-assignation for avoid db crash on unique PK constraint
        return entity
    }

    //UPDATE
    @Update
    abstract fun updateByEntity(entity: Group)

    //DELETE
    @Delete
    abstract  fun deleteByEntity(group: Group)
}