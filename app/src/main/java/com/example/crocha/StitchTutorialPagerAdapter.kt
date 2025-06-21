package com.example.crocha

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class StitchTutorialPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StitchTutorialFragment.newInstance("Single Crochet")
            1 -> StitchTutorialFragment.newInstance("Double Crochet")
            2 -> StitchTutorialFragment.newInstance("Increase")
            3 -> StitchTutorialFragment.newInstance("Decrease")
            4 -> StitchTutorialFragment.newInstance("Magic Ring")
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
} 