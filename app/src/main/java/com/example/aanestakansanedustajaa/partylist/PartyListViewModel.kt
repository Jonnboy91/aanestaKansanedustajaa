package com.example.aanestakansanedustajaa.partylist

//import com.example.aanestakansanedustajaa.repository.ParliamentRepository
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.repository.ParliamentRepository
import com.example.aanestakansanedustajaa.repository.VotingRepository
import kotlinx.coroutines.launch
import java.io.IOException


class PartyListViewModel(application: Application) : AndroidViewModel(application) {

    // Gets the data from ParliamentRepository and then uses that to show only distinct parties
    val parliamentRepository = ParliamentRepository
    val members = parliamentRepository.parliamentData
    val memberparties = Transformations.map(members) {
        members.value?.distinctBy { it.party }
    }

//    private val votingRepository = VotingRepository
//    var votes = votingRepository.votingData


    // LiveData to handle navigation to the selected party
    private val _navigateToSelectedParty = MutableLiveData<ParliamentData>()
    val navigateToSelectedParty: LiveData<ParliamentData>
        get() = _navigateToSelectedParty

    init {
//        refreshDataFromRepository()
//        refreshVotesFromRepository()
    }

//    // Refreshes Database from the API
//    fun refreshVotesFromRepository() {
//        viewModelScope.launch {
//            try {
//                votingRepository.refreshVotingDataEntry()
//            } catch (networkError: IOException) {
//                Toast.makeText(MyApp.appContext, "$networkError",
//                    Toast.LENGTH_LONG).show()
//            }
//        }
//    }


    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                parliamentRepository.refreshParliamentDataEntry()
            } catch (networkError: IOException) {
                Toast.makeText(MyApp.appContext, "$networkError",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    fun displayPartyDetails(memberData: ParliamentData) {
        _navigateToSelectedParty.value = memberData
    }


    //After the navigation has taken place, make sure navigateToSelectedParty is set to null
    fun displayPartyDetailsComplete() {
        _navigateToSelectedParty.value = null
    }

}