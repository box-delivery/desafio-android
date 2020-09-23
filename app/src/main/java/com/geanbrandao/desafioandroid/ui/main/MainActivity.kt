package com.geanbrandao.desafioandroid.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geanbrandao.desafioandroid.R
import com.geanbrandao.desafioandroid.goToActivity
import com.geanbrandao.desafioandroid.ui.splash_screen.SplashScreenActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        goToActivity(SplashScreenActivity::class.java)
    }
}