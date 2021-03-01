package com.example.aanestakansanedustajaa.repository

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.database.ParliamentDatabase
import com.example.aanestakansanedustajaa.internet.ParliamentApi

object ParliamentRepository{
    private val dao = ParliamentDatabase.getInstance(MyApp.appContext).parliamentDatabaseDao
    val parliamentData: LiveData<List<ParliamentData>> = dao.getAllMembers()

    // Refreshes the database from API and checks if there is any new members
    suspend fun refreshParliamentDataEntry() {
        val members = ParliamentApi.retrofitService.getParliamentRecords()
        members.forEach { dao.insertOrUpdate(it) }
    }
}