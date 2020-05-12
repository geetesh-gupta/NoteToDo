package com.gg.notetodo.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return if (position == 0) OnBoardingOneFragment()
        else OnBoardingTwoFragment()
    }

    override fun getCount(): Int {
        return 2
    }

}