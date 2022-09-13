package com.hedaia.socialmediaappomar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hedaia.socialmediaappomar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}