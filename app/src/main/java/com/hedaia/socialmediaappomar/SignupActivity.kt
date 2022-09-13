package com.hedaia.socialmediaappomar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hedaia.socialmediaappomar.databinding.ActivitySignupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class SignupActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            signupBtn.setOnClickListener{
                val username = signupUsernameEt.text.toString()
                val password = signupPasswordEt.text.toString()
                val email = signupEmailEt.text.toString()
                val about = signupAboutEt.text.toString()
                val currentDate = Calendar.getInstance().time
                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZZZZZ").withZone(ZoneId.systemDefault())
                val formattedDate = dateFormatter.format(currentDate.toInstant())

                if (checkEmptyFields(username,password,email))
                {
                    CoroutineScope(Dispatchers.IO).launch {

                        val user = UserDetails(0,
                            email,
                            username,
                            password,
                            "",
                            "",
                            "",
                            about,
                            formattedDate)
                        val response = RetrofitObj.getAPIClient().addNewUser(user)
                        if (response.isSuccessful)
                        {
                            withContext(Dispatchers.Main){
                                val intent = Intent(this@SignupActivity,LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }

                    }
                }

            }



        }


    }

    fun checkEmptyFields(username:String,password:String,email:String):Boolean {

        if (username.isNotEmpty()&&email.isNotEmpty())
            return true
        return false
    }

}