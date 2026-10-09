package com.mikaze.controlpanel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mikaze.controlpanel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.black)
        window.navigationBarColor = getColor(R.color.black)
    }
}
