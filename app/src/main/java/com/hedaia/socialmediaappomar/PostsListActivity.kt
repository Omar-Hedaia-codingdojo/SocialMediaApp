package com.hedaia.socialmediaappomar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hedaia.socialmediaappomar.databinding.ActivityPostsListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsListActivity : AppCompatActivity(),PostListAdapter.PostDetailsListener {
    lateinit var binding: ActivityPostsListBinding
    lateinit var adapter:PostListAdapter
    var apiKey:String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiKey  = intent.getStringExtra("APIKEY")

        adapter = PostListAdapter(this)

        binding.apply {


            postsListRv.adapter = adapter

            addPostFab.setOnClickListener {

                val intent = Intent(this@PostsListActivity,AddPostActivity::class.java)
                intent.putExtra("APIKEY",apiKey)
                startActivity(intent)

            }


        }



    }

    override fun onResume() {

        CoroutineScope(Dispatchers.IO).launch {

            val response = RetrofitObj.getAPIClient().getAllPosts()
            if (response.isSuccessful)
            {
                withContext(Dispatchers.Main){
                    adapter.submitList(response.body())
                }

            }

        }


        super.onResume()
    }

    override fun onPostDetailClicked(post: PostsItem) {
        val intent = Intent(this,PostDetailsActivity::class.java)
        intent.putExtra("post_id",post.id)
        intent.putExtra("APIKEY",apiKey)
        startActivity(intent)

    }
}