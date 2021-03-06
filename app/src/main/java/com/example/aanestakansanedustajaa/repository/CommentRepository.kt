package com.example.aanestakansanedustajaa.repository

import androidx.lifecycle.LiveData
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.database.CommentData
import com.example.aanestakansanedustajaa.database.CommentDatabase
import com.example.aanestakansanedustajaa.internet.CommentApi

object CommentRepository {

    private val dao = CommentDatabase.getInstance(MyApp.appContext).commentDatabaseDao
    val commentData: LiveData<List<CommentData>> = dao.getAllComments()

    // Refreshes the database from API and checks if there are any changes in the votes
    suspend fun refreshCommentDataEntry() {
        val votes = CommentApi.retrofitService.getAllCommentRecords()
        votes.forEach { dao.insertOrUpdate(it) }
    }
    // Registers the vote down to the API
    suspend fun commentDataEntry(id: Int, commentValue: String){
        CommentApi.retrofitService.addComment(id.toString(), commentValue)
    }
}