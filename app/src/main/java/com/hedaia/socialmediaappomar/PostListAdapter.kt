package com.hedaia.socialmediaappomar

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hedaia.socialmediaappomar.databinding.PostItemRowLayoutBinding
import java.lang.StringBuilder

class PostListAdapter(val postDetailsListener: PostDetailsListener):ListAdapter<PostsItem,PostListAdapter.ViewHolder>(PostsDiffUtil()) {
    class ViewHolder(val binding:PostItemRowLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PostItemRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val post = getItem(position)
            postListTitleTv.text = post.title
            val commentsAndLikesString = StringBuilder()
            val comments = post.comments.split(",").filter { it.isNotEmpty() }
            val likes = post.likes.split(",").filter { it.isNotEmpty() }
            val commentsCount = comments.size
            val likesCount = likes.size

            commentsAndLikesString
                .append(commentsCount)
                .append(" Comments")
                .append(" - ")
                .append(likesCount)
                .append(" Likes")

            postListCommentsLikesTv.text = commentsAndLikesString.toString()
            postListDetailsTv.setOnClickListener {

                postDetailsListener.onPostDetailClicked(post)
            }


        }
    }

    interface PostDetailsListener{

        fun onPostDetailClicked(post:PostsItem)

    }

}