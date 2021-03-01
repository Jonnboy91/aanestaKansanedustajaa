package com.example.aanestakansanedustajaa.partylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.adapters.PartyListAdapter
import com.example.aanestakansanedustajaa.databinding.FragmentPartyListBinding


class PartyList : Fragment() {

    private lateinit var viewModel: PartyListViewModel
    private lateinit var binding: FragmentPartyListBinding
    private lateinit var adapterParty: PartyListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_party_list, container, false)
        viewModel = ViewModelProvider(this).get(PartyListViewModel::class.java)
        adapterParty = PartyListAdapter(PartyListAdapter.OnClickListener {
            viewModel.displayPartyDetails(it)
        })

        binding.partyGrid.adapter = adapterParty
        binding.partyGrid.layoutManager = GridLayoutManager(MyApp.appContext, 2)

        // Adds vertical and horizontal dividers so that it is easier to see where the "buttons" change
        binding.partyGrid.addItemDecoration(DividerItemDecoration(MyApp.appContext, DividerItemDecoration.HORIZONTAL ))
        binding.partyGrid.addItemDecoration(DividerItemDecoration(MyApp.appContext, DividerItemDecoration.VERTICAL))

        viewModel.memberparties.observe(viewLifecycleOwner, {
            adapterParty.submitList(it)
        })

        // Navigates to the next fragment and takes the chosen parliamentData to the next fragment, so it can be used there.
        viewModel.navigateToSelectedParty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(PartyListDirections.actionShowMembers(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPartyDetailsComplete()
            }
        })





        return binding.root
    }

}