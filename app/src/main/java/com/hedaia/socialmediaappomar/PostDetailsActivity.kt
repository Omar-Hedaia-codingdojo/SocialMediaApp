package com.hedaia.socialmediaappomar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hedaia.socialmediaappomar.databinding.ActivityAddPostBinding
import com.hedaia.socialmediaappomar.databinding.ActivityPostDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.waitMillis

class PostDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostDetailsBinding
    lateinit var adapter: PostCommentsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postId = intent.getIntExtra("post_id",0)
        val apiKey = intent.getStringExtra("APIKEY")
        adapter = PostCommentsAdapter()
        binding.commentsRv.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {

            val response = RetrofitObj.getAPIClient().getPostDetails(postId)
            if (response.isSuccessful)
            {
                val post = response.body()
                withContext(Dispatchers.Main){
                    binding.apply {

                        if (post!=null){
                            postDetailTitleTv.text = post.title
                            postDetailBodyTv.text = post.text
                            var comments = post.comments
                            var likes = post.likes
                            val commentsList: List<String> = post.comments.split(",").filter { it.isNotEmpty() }
                            val likesList: List<String> = post.likes.split(",").filter { it.isNotEmpty() }
                            adapter.submitList(commentsList)
                            val commentsCount = commentsList.size
                            val likesCount = likesList.size
                            var commentsAndLikesString = StringBuilder()

                            commentsAndLikesString
                                .append(commentsCount)
                                .append(" Comments")
                                .append(" - ")
                                .append(likesCount)
                                .append(" Likes")

                            postDetailCommentLikesTv.text = commentsAndLikesString

                            likeBtn.setOnClickListener {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val userResponse = RetrofitObj.getAPIClient().getUserDetails(apiKey!!)
                                    if (userResponse.isSuccessful){
                                        val userDetail = userResponse.body()
                                        if (!likesList.contains(userDetail!!.username))
                                        {
                                            likes = "${likes},${userDetail.username}"
                                            val updatedPost = PostsItem(comments,postId,likes,post.text,post.title,post.user)
                                            val updateResponse = RetrofitObj.getAPIClient().updatePostDetails(postId,updatedPost)
                                            if (updateResponse.isSuccessful)
                                            {
                                                withContext(Dispatchers.Main){
                                                    commentsAndLikesString = StringBuilder()
                                                    commentsAndLikesString
                                                        .append(commentsCount)
                                                        .append(" Comments")
                                                        .append(" - ")
                                                        .append(likesCount)
                                                        .append(" Likes")

                                                }
                                            }

                                        }
                                    }

                                }


                            }

                            commentBtn.setOnClickListener {

                                val comment = commentEt.text.toString()
                                if (comment.isNotEmpty()){
                                    comments = "${comments},${comment}"
                                    CoroutineScope(Dispatchers.IO).launch{
                                        val updatedPost = PostsItem(comments,postId,likes,post.text,post.title,post.user)
                                        val updateResponse = RetrofitObj.getAPIClient().updatePostDetails(postId,updatedPost)
                                        if (updateResponse.isSuccessful)
                                        {
                                            withContext(Dispatchers.Main){
                                                val newCommentsList: List<String> = comments.split(",").filter { it.isNotEmpty() }
                                                adapter.submitList(newCommentsList)
                                                commentsAndLikesString = StringBuilder()
                                                commentsAndLikesString
                                                    .append(commentsCount)
                                                    .append(" Comments")
                                                    .append(" - ")
                                                    .append(likesCount)
                                                    .append(" Likes")
                                            }
                                        }


                                    }
                                }



                            }

                        }

                    }
                }

            }


        }



    }
}