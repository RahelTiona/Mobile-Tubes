package com.example.crocha

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// > Adapter ini mengatur fragment mana yang akan ditampilkan di setiap tab ViewPager2
class MaterialsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // > Menentukan jumlah total halaman (tab) yang ditampilkan
    override fun getItemCount(): Int = 4 // Benang, Hakpen, Jarum Tapestri, Stitch Marker

    // > Mengembalikan fragment yang sesuai berdasarkan posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BenangFragment()          // > Tab pertama: Fragment untuk Benang
            1 -> HakpenFragment()          // > Tab kedua: Fragment untuk Hakpen
            2 -> JarumTapestriFragment()   // > Tab ketiga: Fragment untuk Jarum Tapestri
            3 -> StitchMarkerFragment()    // > Tab keempat: Fragment untuk Stitch Marker
            else -> throw IllegalStateException("Invalid position $position") // > Error jika posisi tidak valid
        }
    }
}
