package com.example.crocha

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MaterialsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BenangFragment()
            1 -> HakpenFragment()
            2 -> JarumTapestriFragment()
            3 -> StitchMarkerFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
} 