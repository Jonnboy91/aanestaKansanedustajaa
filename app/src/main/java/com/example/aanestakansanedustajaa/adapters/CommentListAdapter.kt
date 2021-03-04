package com.example.aanestakansanedustajaa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.R
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.databinding.GridViewCommentBinding
import com.example.aanestakansanedustajaa.databinding.GridViewMemberBinding
import com.example.aanestakansanedustajaa.repository.CommentRepository
import org.w3c.dom.Comment

class CommentListAdapter: ListAdapter<ParliamentData, CommentListAdapter.CommentViewHolder>(CommentDiffCallback) {

    val commentRepository = CommentRepository
    val commentData = commentRepository.commentData

    var comment: String? = ""
    var date: String? = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = GridViewCommentBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentListAdapter.CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val parliamentData = getItem(position)

        val updatedComment = Transformations.map(commentData){
            commentData.value?.find { it.hetekaID == parliamentData.hetekaId }?.comment
        }
        val updatedDate = Transformations.map(commentData){
            commentData.value?.find { it.hetekaID == parliamentData.hetekaId }?.date
        }
        updatedComment.observeForever { comment = it }
        updatedDate.observeForever { date = it }

        // Setting the date (first 10 are the year-month-date and the comment to show as the text
        holder.binding.voteComment.text = "${date?.take(10) ?: ""}\n${comment ?: "Ei jätetty kommentteja"}"
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