package com.example.aanestakansanedustajaa.partylist

import android.app.Application
import androidx.lifecycle.*
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.repository.ParliamentRepository


class PartyListViewModel(application: Application) : AndroidViewModel(application) {

    // Gets the data from ParliamentRepository and then uses that to show only distinct parties
    val parliamentRepository = ParliamentRepository
    val members = parliamentRepository.parliamentData
    val memberparties = Transformations.map(members) {
        members.value?.distinctBy { it.party }
    }

    // LiveData to handle navigation to the selected party
    private val _navigateToSelectedParty = MutableLiveData<ParliamentData?>()
    val navigateToSelectedParty: LiveData<ParliamentData?>
        get() = _navigateToSelectedParty

    fun displayPartyDetails(memberData: ParliamentData) {
        _navigateToSelectedParty.value = memberData
    }

    //After the navigation has taken place, make sure navigateToSelectedParty is set to null
    fun displayPartyDetailsComplete() {
        _navigateToSelectedParty.value = null
    }

}