package com.hedaia.socialmediaappomar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hedaia.socialmediaappomar.databinding.ActivityAddPostBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.waitMillis

class AddPostActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiKey = intent.getStringExtra("APIKEY")

        binding.apply {

            addPostBtn.setOnClickListener {

                val postTitle = addPostTitleEt.text.toString()
                val postBody = addPostTextEt.text.toString()

                CoroutineScope(Dispatchers.IO).launch {

                    val response = RetrofitObj.getAPIClient().getUserDetails(apiKey!!)
                    if (response.isSuccessful)
                    {
                        val user = response.body()
                        if(user != null)
                        {
                            val userName = user.username

                            val post = PostsItem("",0,"",postBody,postTitle,userName)
                            val addPostResponse = RetrofitObj.getAPIClient().addNewPost(post)
                            if (addPostResponse.isSuccessful)
                            {
                                withContext(Dispatchers.Main){
                                    finish()
                                }
                            }

                        }



                    }

                }





            }

        }


    }
}