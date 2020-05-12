package com.gg.notetodo.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gg.notetodo.R
import com.gg.notetodo.util.PrefConstant
import com.gg.notetodo.util.StoreSession

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        StoreSession.init(this)
        val isLoggedIn = StoreSession.read(PrefConstant.IS_LOGGED_IN)
        val isBoardingSuccess = StoreSession.read(PrefConstant.ON_BOARDED_SUCCESSFUL)

        if (isLoggedIn!!) {
            val intent = Intent(this@SplashActivity, ToDoActivity::class.java)
            startActivity(intent)
        } else {
//            if (isBoardingSuccess!!) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
//            } else {
//                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
//                startActivity(intent)
//            }
        }
        finish()
    }
}
