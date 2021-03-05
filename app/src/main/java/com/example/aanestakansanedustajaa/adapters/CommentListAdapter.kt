package com.example.aanestakansanedustajaa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.databinding.GridViewCommentBinding
import com.example.aanestakansanedustajaa.repository.CommentRepository

class CommentListAdapter(private val lifecycleOwner: LifecycleOwner): ListAdapter<ParliamentData, CommentListAdapter.CommentViewHolder>(CommentDiffCallback) {

    val commentRepository = CommentRepository
    val commentData = commentRepository.commentData


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = GridViewCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val parliamentData = getItem(position)

        // Setting the date (first 10 are the year-month-date and the comment to show as the text
        commentData.observe(lifecycleOwner, Observer {
            holder.binding.voteComment.text = "${it.find { it.hetekaID == parliamentData.hetekaId }?.date?.take(10) ?: ""} ${it.find { it.hetekaID == parliamentData.hetekaId }?.comment ?: "Ei kommentteja"}"
        })

    }

    class CommentViewHolder(val binding: GridViewCommentBinding)
        :RecyclerView.ViewHolder(binding.root)

    companion object CommentDiffCallback: DiffUtil.ItemCallback<ParliamentData>() {
        override fun areItemsTheSame(oldItem: ParliamentData, newItem: ParliamentData): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: ParliamentData, newItem: ParliamentData): Boolean {
            return oldItem.hetekaId == newItem.hetekaId
        }
    }
}