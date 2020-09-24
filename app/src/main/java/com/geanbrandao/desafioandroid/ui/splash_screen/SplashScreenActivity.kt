package com.geanbrandao.desafioandroid.ui.splash_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geanbrandao.desafioandroid.R
import com.geanbrandao.desafioandroid.goToActivity
import com.geanbrandao.desafioandroid.ui.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        goToActivity(HomeActivity::class.java)
    }


}