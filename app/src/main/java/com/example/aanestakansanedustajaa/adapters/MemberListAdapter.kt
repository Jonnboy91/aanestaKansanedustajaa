package com.example.aanestakansanedustajaa.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.bindImage
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.databinding.GridViewMemberBinding
import com.example.aanestakansanedustajaa.memberlist.MemberList
import com.example.aanestakansanedustajaa.repository.VotingRepository

class MemberListAdapter(private val onClickListener: OnClickListener): ListAdapter<ParliamentData, MemberListAdapter.MemberViewHolder>(MemberDiffCallback) {

    private val votingRepository = VotingRepository
    var votes = votingRepository.votingData

    var scores: Int? = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = GridViewMemberBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {

        val parliamentData = getItem(position)

        val updatedVote = Transformations.map(votes){
            votes.value?.find { it.hetekaID == parliamentData.hetekaId }?.score
        }

        updatedVote.observeForever { scores = it }


        // Showing members name
        holder.binding.members.text = "${parliamentData.firstname} ${parliamentData.lastname}"
        // Showing members score
        holder.binding.likeResult.text = "Score: ${votes.value?.find { it.hetekaID == parliamentData.hetekaId }?.score  ?: "---"} $scores"
        // Showing members image
        bindImage(holder.binding.photoMember, "https://avoindata.eduskunta.fi/${parliamentData.pictureUrl}")
        // OnClick event for choosing a member and then transferring the info to MemberDetails fragment
        holder.itemView.setOnClickListener { onClickListener.onClick(parliamentData) }
    }

    companion object MemberDiffCallback : DiffUtil.ItemCallback<ParliamentData>() {
        override fun areItemsTheSame(oldItem: ParliamentData, newItem: ParliamentData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ParliamentData, newItem: ParliamentData): Boolean {
            return oldItem.hetekaId == newItem.hetekaId
        }

    }

    class MemberViewHolder(val binding: GridViewMemberBinding)
        :RecyclerView.ViewHolder(binding.root)

    class OnClickListener(val clickListener: (parliamentParty: ParliamentData) -> Unit) {
        fun onClick(parliamentParty: ParliamentData) = clickListener(parliamentParty)
    }

}