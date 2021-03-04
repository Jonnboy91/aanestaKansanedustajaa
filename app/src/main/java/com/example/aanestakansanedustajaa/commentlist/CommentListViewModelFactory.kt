package com.example.aanestakansanedustajaa.commentlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aanestakansanedustajaa.database.ParliamentData

class CommentListViewModelFactory (private val parliamentData: ParliamentData, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentListViewModel::class.java)) {
            return CommentListViewModel(parliamentData, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}