package com.example.aanestakansanedustajaa.memberdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.bindImage
import com.example.aanestakansanedustajaa.databinding.FragmentMemberDetailsBinding



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
        binding.sendComment.setOnClickListener{ comment() }
        binding.readComments.setOnClickListener{ readComments()}

        // Sets the info for name, party, logo and the member photo
        binding.memberName.text = viewModel.fullName
        binding.partyName.text = viewModel.party
        binding.partyLogo.setImageResource(viewModel.partyLogo)
        bindImage(binding.memberPhoto, "https://avoindata.eduskunta.fi/${viewModel.chosenMember?.pictureUrl}")

        // Observes the vote record from the repository
        viewModel.updatedVote.observe(viewLifecycleOwner, Observer {
            binding.voteResult.text = "Score: $it"
        })

        return binding.root
    }

    // Once clicked it will give the hetekaId information of the person who's button was clicked and then deducts points through viewmodel to the API
    private fun onDown() {
        viewModel.chosenMember?.let { viewModel.onDown(it.hetekaId) }
        binding.commentBox.visibility = View.VISIBLE
        binding.sendComment.visibility = View.VISIBLE
        binding.voteDown.visibility = View.GONE
        binding.voteUp.visibility = View.GONE
        binding.readComments.visibility = View.GONE
    }

    // Once clicked it will give the hetekaId information of the person who's button was clicked and then adds points through viewmodel to the API
    private fun onUp() {
        viewModel.chosenMember?.let { viewModel.onUp(it.hetekaId) }
        binding.commentBox.visibility = View.VISIBLE
        binding.sendComment.visibility = View.VISIBLE
        binding.voteDown.visibility = View.GONE
        binding.voteUp.visibility = View.GONE
        binding.readComments.visibility = View.GONE
    }

    // Once clicked it will give the hetekaId information of the person who's button was clicked and the comment in the commentbox and saves that to the API
    private fun comment() {
        viewModel.chosenMember?.let { viewModel.comment(it.hetekaId, binding.commentBox.text.toString()) }
        binding.commentBox.visibility = View.GONE
        binding.sendComment.visibility = View.GONE
        binding.voteDown.visibility = View.VISIBLE
        binding.voteUp.visibility = View.VISIBLE
        binding.readComments.visibility = View.VISIBLE
        binding.commentBox.text?.clear()
    }

    // Once clicked it will transfer the parliamentmemberdata to the next fragment
    private fun readComments(){
        viewModel.chosenMember?.let {
            this.findNavController().navigate(MemberDetailsDirections.actionShowComments(it))
        }
    }

}