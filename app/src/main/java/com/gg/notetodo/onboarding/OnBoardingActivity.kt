package com.gg.notetodo.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.gg.notetodo.R
import com.gg.notetodo.util.PrefConstant
import com.gg.notetodo.util.StoreSession
import com.gg.notetodo.views.LoginActivity

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick,
    OnBoardingTwoFragment.OnOptionClick {

    private lateinit var viewPager: ViewPager
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindView()
        setupSharedPreference()
    }

    private fun setupSharedPreference() {
        sharedPreferences =
            getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun bindView() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }

    override fun onOptionDone() {
        //2nd fragment
        StoreSession.init(this)
        StoreSession.write(PrefConstant.ON_BOARDED_SUCCESSFUL, true)

        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}