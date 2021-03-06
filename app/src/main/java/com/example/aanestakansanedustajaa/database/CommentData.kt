package com.example.aanestakansanedustajaa.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "comment_database")
data class CommentData(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "hetekaID")
    val hetekaID: Int,
    @ColumnInfo(name = "comment")
    val comment: String
)