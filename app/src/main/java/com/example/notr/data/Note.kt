package com.example.notr.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "NotesTable")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val entry: String,
    //@ColumnInfo(name = "date_added")
    //val dateAdded: Date
)