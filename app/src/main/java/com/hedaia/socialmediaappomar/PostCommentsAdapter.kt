package com.hedaia.socialmediaappomar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hedaia.socialmediaappomar.databinding.CommentRowLayoutBinding
import com.hedaia.socialmediaappomar.databinding.PostItemRowLayoutBinding
import java.lang.StringBuilder

class PostCommentsAdapter:ListAdapter<String,PostCommentsAdapter.ViewHolder>(CommentsDiffUtil()) {
    class ViewHolder(val binding:CommentRowLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CommentRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

            val comment = getItem(position)

            commentTv.text = comment

        }
    }


}