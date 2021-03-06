package com.example.aanestakansanedustajaa.memberlist

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.repository.ParliamentRepository
import com.example.aanestakansanedustajaa.repository.VotingRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MemberListViewModel(parliamentData: ParliamentData, application: Application) : AndroidViewModel(application) {

    private val votingRepository = VotingRepository

    val parliamentRepository = ParliamentRepository
    var members = parliamentRepository.parliamentData
    val chosenParty = Transformations.map(members){
        members.value?.filter { it.party == parliamentData.party }
    }

    // LiveData to handle navigation to the selected member
    private val _navigateToSelectedMember = MutableLiveData<ParliamentData?>()
    val navigateToSelectedMember: LiveData<ParliamentData?>
        get() = _navigateToSelectedMember

    // Initialize the _selectedProperty MutableLiveData
    init {
        refreshVotesFromRepository()
    }

    fun displayMemberDetails(memberData: ParliamentData) {
        _navigateToSelectedMember.value = memberData
    }

    //After the navigation has taken place, make sure navigateToSelectedParty is set to null
    fun displayMemberDetailsComplete() {
        _navigateToSelectedMember.value = null
    }

    // Refreshes Database from the API
    fun refreshVotesFromRepository() {
        viewModelScope.launch {
            try {
                votingRepository.refreshVotingDataEntry()
            } catch (networkError: IOException) {
                Toast.makeText(
                    MyApp.appContext, "$networkError",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

}