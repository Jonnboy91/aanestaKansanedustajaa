package com.example.aanestakansanedustajaa.commentlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.adapters.CommentListAdapter
import com.example.aanestakansanedustajaa.databinding.FragmentCommentListBinding

class CommentList : Fragment() {

    private lateinit var viewModel: CommentListViewModel
    private lateinit var binding: FragmentCommentListBinding
    private lateinit var adapterComment: CommentListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val parliamentData = CommentListArgs.fromBundle(requireArguments()).selectedMemberComments
        val viewModelFactory = CommentListViewModelFactory(parliamentData, application)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment_list, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CommentListViewModel::class.java)
        adapterComment = CommentListAdapter(this)

        binding.voteGrid.adapter = adapterComment
        binding.voteGrid.layoutManager = LinearLayoutManager(MyApp.appContext)

        //Adds vertical divider for the itemViews
        binding.voteGrid.addItemDecoration(DividerItemDecoration(MyApp.appContext, DividerItemDecoration.VERTICAL))

        viewModel.selectedMemberComments.observe(viewLifecycleOwner, {
            adapterComment.submitList(it)
        })

        return binding.root
    }

}