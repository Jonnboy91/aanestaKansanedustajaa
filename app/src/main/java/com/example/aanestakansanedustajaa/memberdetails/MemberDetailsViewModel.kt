package com.example.aanestakansanedustajaa.memberdetails

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.database.VotingData
import com.example.aanestakansanedustajaa.database.VotingDatabase
import com.example.aanestakansanedustajaa.repository.ParliamentRepository
import com.example.aanestakansanedustajaa.repository.VotingRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MemberDetailsViewModel(parliamentData: ParliamentData, application: Application) : AndroidViewModel(application) {

    private val parliamentRepository = ParliamentRepository
    var members = parliamentRepository.parliamentData
    val chosenMember = members.value?.find { it.hetekaId == parliamentData.hetekaId }

    private val votingRepository = VotingRepository
    var votes = votingRepository.votingData

    // The internal MutableLiveData for the selected property
    private val selectedMember = MutableLiveData<ParliamentData>()

    var name: String = ""
    var party: String = ""
    var partyLogo: Int = R.drawable.ic_launcher_foreground

//    var score = MutableLiveData<Int>()
//        private set

    init {

        selectedMember.value = chosenMember

        refreshVotesFromRepository()

        //Simpler for the fragment view when name is already "added" here
        name = "${selectedMember.value?.firstname} ${selectedMember.value?.lastname}"

        // Setting the name of the party from the abbreviation to the full name
        party = when (selectedMember.value?.party){
            "vihr" -> "Vihreä liitto"
            "ps" -> "Perussuomalaiset"
            "kesk" -> "Suomen Keskusta"
            "kok" -> "Kansallinen Kokoomus"
            "sd" -> "Suomen Sosialidemokraattinen Puolue"
            "vas" -> "Vasemmistoliitto"
            "kd" -> "Suomen Kristillisdemokraatit"
            "r" -> "Suomen ruotsalainen kansanpuolue"
            else -> "Liike Nyt"
        }

        // Checking the party name and using the right logo for that
        partyLogo = when(selectedMember.value?.party){
            "vihr" -> R.drawable.vihr
            "ps" -> R.drawable.ps
            "kesk" -> R.drawable.kesk
            "kok" -> R.drawable.kok
            "sd" -> R.drawable.sd
            "vas" -> R.drawable.vas
            "kd" -> R.drawable.kd
            "r" -> R.drawable.r
            else -> R.drawable.liike
        }

//        score = votes.value.find { it.hetekaID == parliamentData.hetekaId }.score
    }

    // Refreshes Database from the API
    fun refreshVotesFromRepository() {
        viewModelScope.launch {
            try {
                votingRepository.refreshVotingDataEntry()
            } catch (networkError: IOException) {
                Toast.makeText(MyApp.appContext, "$networkError",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    // Calls the Repository, which then calls Retrofit to add points also to the Api and then refreshes database
    fun onUp(id: Int) {
        viewModelScope.launch {
            try {
                votingRepository.voteUpDataEntry(id)
                selectedMember.value?.let { refreshVotesFromRepository() }
            } catch (networkError: IOException) {
                Toast.makeText(MyApp.appContext, "$networkError",
                    Toast.LENGTH_LONG).show()
            }
        }

    }

    // Calls the Repository, which then calls Retrofit to deducts points also to the Api and then refreshes database
    fun onDown(id: Int) {
        viewModelScope.launch {
            try {
                votingRepository.voteDownDataEntry(id)
                selectedMember.value?.let { refreshVotesFromRepository() }
            } catch (networkError: IOException) {
                Toast.makeText(MyApp.appContext, "$networkError",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

}