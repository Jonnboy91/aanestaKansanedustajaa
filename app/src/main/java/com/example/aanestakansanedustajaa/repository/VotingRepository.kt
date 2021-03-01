package com.example.aanestakansanedustajaa.repository

import androidx.lifecycle.LiveData
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.database.VotingData
import com.example.aanestakansanedustajaa.database.VotingDatabase
import com.example.aanestakansanedustajaa.internet.VotingApi

object VotingRepository{
    private val dao = VotingDatabase.getInstance(MyApp.appContext).votingDatabaseDao
    val votingData: LiveData<List<VotingData>> = dao.getAllVotes()

    // Refreshes the database from API and checks if there are any changes in the votes
    suspend fun refreshVotingDataEntry() {
        val votes = VotingApi.retrofitService.getAllVotingRecords()
        votes.forEach { dao.insertOrUpdate(it) }
    }
    // Registers the vote up to the API
    suspend fun voteUpDataEntry(id: Int){
        VotingApi.retrofitService.voteUp(id.toString())
    }
    // Registers the vote down to the API
    suspend fun voteDownDataEntry(id: Int){
        VotingApi.retrofitService.voteDown(id.toString())
    }
}