package com.example.crocha

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class CrochaStitchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crocha_stitch)
        
        findViewById<View>(R.id.stitchCard).setOnClickListener {
            val intent = Intent(this, StitchTutorialActivity::class.java)
            startActivity(intent)
        }
        
        findViewById<View>(R.id.materialsCard).setOnClickListener {
            val intent = Intent(this, MaterialsActivity::class.java)
            startActivity(intent)
        }
    }
} 