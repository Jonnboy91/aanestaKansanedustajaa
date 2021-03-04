package com.example.aanestakansanedustajaa.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDatabaseDao {
    // Checks the database for conflicts, if none then inserts the comment there
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(comment: CommentData)

    // Return a list from the database ordered by date
    @Query("SELECT * FROM comment_database")
    fun getAllComments(): LiveData<List<CommentData>>
}