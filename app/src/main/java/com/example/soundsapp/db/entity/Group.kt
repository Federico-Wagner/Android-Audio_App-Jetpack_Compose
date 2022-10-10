package com.example.soundsapp.db.entity

import androidx.room.*

@Entity(tableName = "groups")
data class Group (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "group_name") var groupName: String
)

