package com.example.soundsapp.db.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.soundsapp.db.entity.Group


@Dao
abstract class GroupDAO {
    @Query("SELECT * FROM groups")
    abstract fun getAll(): List<Group>

    @Query("SELECT * FROM groups WHERE groups.group_name = :groupName")
    abstract fun getGroupByName(groupName : String): Group?

    fun insert(entity: Group): Group {
        entity.groupId = getNextId(entity) //id re-assignation for avoid db crash on unique PK constraint
        return entity
    }
    @Insert
    abstract fun getNextId(entity: Group): Long

    @Delete
    abstract  fun deleteByEntity(group: Group)


}