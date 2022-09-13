package com.hedaia.socialmediaappomar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hedaia.socialmediaappomar.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            signupTv.setOnClickListener {
                val intent = Intent(this@LoginActivity,SignupActivity::class.java)
                startActivity(intent)
                finish()


            }

            loginBtn.setOnClickListener {
                val username = usernameEt.text.toString()
                val password = passwordEt.text.toString()
                if (checkUserNameAndPass(username,password))
                {
                    logInWithUserNameAndPassword(username,password)
                }


            }

        }


    }

    fun checkUserNameAndPass(username:String,pass:String):Boolean
    {
        if (username.isEmpty())
        {
           return  false
        }
        return true

    }

    fun logInWithUserNameAndPassword(username: String,pass: String)
    {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitObj.getAPIClient().getUserApiKey(username,pass)
            if (response.isSuccessful && !response.body().isNullOrEmpty())
            {
                val apiKey = response.body()
                withContext(Dispatchers.Main){
                    val intent = Intent(this@LoginActivity,PostsListActivity::class.java)
                    intent.putExtra("APIKEY",apiKey)
                    startActivity(intent)
                }
            }

        }


    }
}