package com.example.aanestakansanedustajaa.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VotingDatabaseDao {
    // Checks the database for conflicts, if none then inserts the votingData there
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(voting: VotingData)

    // Gets a list of all the votes in the database
    @Query("SELECT * FROM voting_database")
    fun getAllVotes(): LiveData<List<VotingData>>

}