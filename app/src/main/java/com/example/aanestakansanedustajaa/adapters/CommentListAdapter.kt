package com.example.aanestakansanedustajaa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aanestakansanedustajaa.database.CommentData
import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.databinding.GridViewCommentBinding
import com.example.aanestakansanedustajaa.repository.CommentRepository

class CommentListAdapter: ListAdapter<CommentData, CommentListAdapter.CommentViewHolder>(CommentDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = GridViewCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val commentItem = getItem(position)

        // Getting the date (first 10 char. to get year-month-date) and the comment.
        holder.binding.voteComment.text = "${commentItem.date.take(10)}\n${commentItem.comment}"

    }

    class CommentViewHolder(val binding: GridViewCommentBinding)
        :RecyclerView.ViewHolder(binding.root)

    companion object CommentDiffCallback: DiffUtil.ItemCallback<CommentData>() {
        override fun areItemsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
            return oldItem.date == newItem.date
        }
    }
}