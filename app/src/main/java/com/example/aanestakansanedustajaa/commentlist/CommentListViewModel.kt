package com.example.aanestakansanedustajaa.commentlist

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.database.CommentData
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.repository.CommentRepository
import com.example.aanestakansanedustajaa.repository.ParliamentRepository
import kotlinx.coroutines.launch
import java.io.IOException

class CommentListViewModel(parliamentData: ParliamentData, application: Application) : AndroidViewModel(application) {

    val commentRepository = CommentRepository
    var membersComments = commentRepository.commentData

    val chosenMemberComments = Transformations.map(membersComments){
        membersComments.value?.filter { it.hetekaID == parliamentData.hetekaId }
    }

    // Initialize the _selectedProperty MutableLiveData
    init {
        refreshCommentsFromRepository()
    }


    fun refreshCommentsFromRepository() {
        viewModelScope.launch {
            try {
                commentRepository.refreshCommentDataEntry()
            } catch (networkError: IOException) {
                Toast.makeText(
                    MyApp.appContext, "$networkError",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

}