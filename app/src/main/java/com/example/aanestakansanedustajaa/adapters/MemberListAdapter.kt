package com.example.aanestakansanedustajaa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aanestakansanedustajaa.bindImage
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.databinding.GridViewMemberBinding
import com.example.aanestakansanedustajaa.repository.VotingRepository

class MemberListAdapter(private val lifecycle : LifecycleOwner, private val onClickListener: OnClickListener): ListAdapter<ParliamentData, MemberListAdapter.MemberViewHolder>(MemberDiffCallback) {

    private val votingRepository = VotingRepository
    val votes = votingRepository.votingData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = GridViewMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {

        val memberItem = getItem(position)

        // Showing members name
        holder.binding.members.text = "${memberItem.firstname} ${memberItem.lastname}"
        // Showing members score (Observing it to update the value to the right one on the first view)
        votes.observe(lifecycle, Observer {
            holder.binding.likeResult.text = "Score: ${votes.value?.find { it.hetekaID == memberItem.hetekaId }?.score}"
        })
        // Showing members image
        bindImage(holder.binding.photoMember, "https://avoindata.eduskunta.fi/${memberItem.pictureUrl}")
        // OnClick event for choosing a member and then transferring the info to MemberDetails fragment
        holder.itemView.setOnClickListener { onClickListener.onClick(memberItem) }
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