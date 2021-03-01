package com.example.aanestakansanedustajaa.memberdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.bindImage
import com.example.aanestakansanedustajaa.databinding.FragmentMemberDetailsBinding
//import kotlinx.android.synthetic.main.fragment_member_details.*


class MemberDetails : Fragment() {

    private lateinit var viewModel: MemberDetailsViewModel
    private lateinit var binding: FragmentMemberDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val parliamentData = MemberDetailsArgs.fromBundle(requireArguments()).selectedMember
        val viewModelFactory = MemberDetailsViewModelFactory(parliamentData, application)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_details, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MemberDetailsViewModel::class.java)

        binding.voteUp.setOnClickListener { onUp() }
        binding.voteDown.setOnClickListener { onDown() }

        // Sets the info for name, party, logo and the member photo
        binding.memberName.text = viewModel.name
        binding.partyName.text = viewModel.party
        binding.partyLogo.setImageResource(viewModel.partyLogo)
        bindImage(binding.memberPhoto, "https://avoindata.eduskunta.fi/${viewModel.chosenMember?.pictureUrl}")

        // Observes the vote record from the repository
        viewModel.votes.observe(viewLifecycleOwner, Observer {
            binding.voteResult.text = it.find { it.hetekaID == parliamentData.hetekaId }?.score.toString()
        })

        return binding.root
    }

    // Once clicked it will give the hetekaId information of the person who's button was clicked and then deducts points through viewmodel to the API
    private fun onDown() {
        viewModel.chosenMember?.let { viewModel.onDown(it.hetekaId) }
    }

    // Once clicked it will give the hetekaId information of the person who's button was clicked and then adds points through viewmodel to the API
    private fun onUp() {
        viewModel.chosenMember?.let { viewModel.onUp(it.hetekaId) }
    }

}