package com.example.aanestakansanedustajaa.memberlist

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.adapters.MemberListAdapter
import com.example.aanestakansanedustajaa.databinding.FragmentMemberListBinding
import com.example.aanestakansanedustajaa.memberdetails.MemberDetailsViewModel
import com.example.aanestakansanedustajaa.memberdetails.MemberDetailsViewModelFactory

class MemberList : Fragment() {

    private lateinit var viewModel: MemberListViewModel
    private lateinit var binding: FragmentMemberListBinding
    private lateinit var adapterMember: MemberListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val parliamentData = MemberListArgs.fromBundle(requireArguments()).selectedParty
        val viewModelFactory = MemberListViewModelFactory(parliamentData, application)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MemberListViewModel::class.java)
        adapterMember = MemberListAdapter(MemberListAdapter.OnClickListener {
            viewModel.displayMemberDetails(it)
        })

        binding.memberGrid.adapter = adapterMember
        binding.memberGrid.layoutManager = LinearLayoutManager(MyApp.appContext)

        //Adds vertical divider for the itemViews
        binding.memberGrid.addItemDecoration(DividerItemDecoration(MyApp.appContext, DividerItemDecoration.VERTICAL))

        viewModel.selectedParty.observe(viewLifecycleOwner, {
            adapterMember.submitList(it)
        })

        viewModel.votes.observe(viewLifecycleOwner, Observer {

        })

        // Navigates to the next fragment and takes the chosen parliamentData to the next fragment, so it can be used there.
        viewModel.navigateToSelectedMember.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(MemberListDirections.actionShowDetails(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayMemberDetailsComplete()
            }
        })

        return binding.root
    }

}