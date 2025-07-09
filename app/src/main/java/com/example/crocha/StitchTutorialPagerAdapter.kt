package com.example.crocha

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Adapter yang digunakan untuk menghubungkan ViewPager2 dengan Fragment yang berbeda-beda
class StitchTutorialPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Command line: > Memanggil getItemCount() untuk menentukan jumlah tab
    override fun getItemCount(): Int = 5

    // Command line: > Membuat Fragment sesuai dengan posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            // Command line: > Posisi 0 -> Membuat Fragment untuk Single Crochet
            0 -> StitchTutorialFragment.newInstance("Single Crochet")

            // Command line: > Posisi 1 -> Membuat Fragment untuk Double Crochet
            1 -> StitchTutorialFragment.newInstance("Double Crochet")

            // Command line: > Posisi 2 -> Membuat Fragment untuk Increase
            2 -> StitchTutorialFragment.newInstance("Increase")

            // Command line: > Posisi 3 -> Membuat Fragment untuk Decrease
            3 -> StitchTutorialFragment.newInstance("Decrease")

            // Command line: > Posisi 4 -> Membuat Fragment untuk Magic Ring
            4 -> StitchTutorialFragment.newInstance("Magic Ring")

            // Command line: > Posisi tidak valid, melempar error
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}
