package com.example.crocha

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class StitchTutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Command line: > Mengaktifkan layout yang memperhitungkan sistem UI (status bar, dll)
        enableEdgeToEdge()

        // Command line: > Menghubungkan aktivitas dengan layout XML activity_stitch_tutorial.xml
        setContentView(R.layout.activity_stitch_tutorial)

        // Command line: > Ambil referensi ke tabLayout dari XML
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        // Command line: > Ambil referensi ke viewPager dari XML
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // Command line: > Pasang adapter untuk mengisi ViewPager2 dengan Fragment
        viewPager.adapter = StitchTutorialPagerAdapter(this)

        // Command line: > Sinkronisasi TabLayout dan ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Command line: > Atur teks tab berdasarkan posisi
            tab.text = when (position) {
                0 -> "Single Crochet"
                1 -> "Double Crochet"
                2 -> "Increase"
                3 -> "Decrease"
                4 -> "Magic Ring"
                else -> null
            }
            // Command line: > Aktifkan sinkronisasi tab dan halaman
        }.attach()
    }
}
