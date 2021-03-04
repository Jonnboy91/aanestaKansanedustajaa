package com.example.aanestakansanedustajaa.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.databinding.GridViewMemberBinding
import com.example.aanestakansanedustajaa.databinding.GridViewPartyBinding

class PartyListAdapter(private val onClickListener: OnClickListener): ListAdapter<ParliamentData, PartyListAdapter.PartyViewHolder>(PartyDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
//        val itemView = LayoutInflater.from(MyApp.appContext).inflate(R.layout.grid_view_party, parent, false)
//        return PartyViewHolder(itemView)
        val binding = GridViewPartyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PartyListAdapter.PartyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {

        val parliamentData = getItem(position)

        // Setting the name of the party from the abbreviation to the full name
        holder.binding.party.text = when (parliamentData.party){
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
        holder.binding.logoParty.setImageResource(when (parliamentData.party){
            "vihr" -> R.drawable.vihr
            "ps" -> R.drawable.ps
            "kesk" -> R.drawable.kesk
            "kok" -> R.drawable.kok
            "sd" -> R.drawable.sd
            "vas" -> R.drawable.vas
            "kd" -> R.drawable.kd
            "r" -> R.drawable.r
            else -> R.drawable.liike
        })

        // OnClick event for choosing a member and then transferring the info to MemberList fragment
        holder.itemView.setOnClickListener { onClickListener.onClick(parliamentData) }
    }

    class PartyViewHolder(val binding: GridViewPartyBinding)
        :RecyclerView.ViewHolder(binding.root)

    companion object PartyDiffCallback: DiffUtil.ItemCallback<ParliamentData>() {
        override fun areItemsTheSame(oldItem: ParliamentData, newItem: ParliamentData): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: ParliamentData, newItem: ParliamentData): Boolean {
            return oldItem.hetekaId == newItem.hetekaId
        }
    }

    class OnClickListener(val clickListener: (parliamentParty:ParliamentData) -> Unit) {
        fun onClick(parliamentParty:ParliamentData) = clickListener(parliamentParty)
    }
}



