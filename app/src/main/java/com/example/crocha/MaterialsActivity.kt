package com.example.crocha

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MaterialsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // > Mengaktifkan fitur edge-to-edge display (layar penuh)
        enableEdgeToEdge()

        // > Menentukan tampilan layout utama untuk activity ini
        setContentView(R.layout.activity_materials)

        // > Menghubungkan variabel ke komponen TabLayout dan ViewPager2 dari XML
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // > Menetapkan adapter untuk ViewPager agar dapat menampilkan fragment berdasarkan tab
        viewPager.adapter = MaterialsPagerAdapter(this)

        // > Menghubungkan TabLayout dan ViewPager agar sinkron (judul tab sesuai dengan halaman)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Benang"           // > Tab pertama
                1 -> "Hakpen"           // > Tab kedua
                2 -> "Jarum Tapestri"   // > Tab ketiga
                3 -> "Stitch Marker"    // > Tab keempat
                else -> null
            }
        }.attach() // > Terapkan mediator agar TabLayout dan ViewPager bekerja bersama
    }
}
