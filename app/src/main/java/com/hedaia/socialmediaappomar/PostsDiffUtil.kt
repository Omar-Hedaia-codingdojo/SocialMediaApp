package com.hedaia.socialmediaappomar

import androidx.recyclerview.widget.DiffUtil

class PostsDiffUtil: DiffUtil.ItemCallback<PostsItem>() {
    override fun areItemsTheSame(oldItem: PostsItem, newItem: PostsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostsItem, newItem: PostsItem): Boolean {
        return oldItem == newItem
    }
}