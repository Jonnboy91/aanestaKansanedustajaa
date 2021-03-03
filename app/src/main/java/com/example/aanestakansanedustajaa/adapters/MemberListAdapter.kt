package com.example.aanestakansanedustajaa.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.bindImage
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.memberlist.MemberList
import com.example.aanestakansanedustajaa.repository.VotingRepository

class MemberListAdapter(private val onClickListener: OnClickListener): ListAdapter<ParliamentData, MemberListAdapter.MemberViewHolder>(PartyListAdapter) {

    private val votingRepository = VotingRepository
    var votes = votingRepository.votingData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val itemView = LayoutInflater.from(MyApp.appContext).inflate(R.layout.grid_view_member, parent, false)
        return MemberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        // could use binding?
        val parliamentData = getItem(position)
        // Showing members name
        holder.itemView.findViewById<TextView>(R.id.members).text = "${parliamentData.firstname} ${parliamentData.lastname}"
        // Showing members score
        holder.itemView.findViewById<TextView>(R.id.likeResult).text = "Score: ${votes.value?.find { it.hetekaID == parliamentData.hetekaId }?.score.toString()}"
        // Showing members image
        bindImage(holder.itemView.findViewById(R.id.photo_member), "https://avoindata.eduskunta.fi/${parliamentData.pictureUrl}")
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

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class OnClickListener(val clickListener: (parliamentParty: ParliamentData) -> Unit) {
        fun onClick(parliamentParty: ParliamentData) = clickListener(parliamentParty)
    }

}