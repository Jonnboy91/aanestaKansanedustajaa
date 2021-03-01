package com.example.aanestakansanedustajaa.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParliamentDatabaseDao {
    // Checks the database for conflicts, if none then inserts the parliamentData there
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(parliament: ParliamentData)

    // Return a list from the database ordered by last_name
    @Query("SELECT * FROM parliament_member_database ORDER BY last_name")
    fun getAllMembers(): LiveData<List<ParliamentData>>
}