package com.example.aanestakansanedustajaa.commentlist

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.repository.CommentRepository
import com.example.aanestakansanedustajaa.repository.ParliamentRepository
import kotlinx.coroutines.launch
import java.io.IOException

class CommentListViewModel(parliamentData: ParliamentData, application: Application) : AndroidViewModel(application) {

    val parliamentRepository = ParliamentRepository
    var membersdata = parliamentRepository.parliamentData
    val chosenData = membersdata.value?.filter { it.hetekaId == parliamentData.hetekaId }

    val commentRepository = CommentRepository
    var members = commentRepository.commentData
    val chosenMember = members.value?.filter { it.hetekaID == parliamentData.hetekaId }

    // The internal MutableLiveData for the selected property
    private val _selectedMemberComments = MutableLiveData<List<ParliamentData>>()

    // The external LiveData for the SelectedProperty
    val selectedMemberComments: LiveData<List<ParliamentData>>
        get() = _selectedMemberComments

    // Initialize the _selectedProperty MutableLiveData
    init {
        refreshCommentsFromRepository()
        _selectedMemberComments.value = chosenData
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