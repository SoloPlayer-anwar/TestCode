package com.testing.testseucom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.testing.testseucom.databinding.ActivityMainBinding
import com.testing.testseucom.ui.ListActivity

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finishAffinity()
        },2000)

    }

}