package com.example.crocha

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar

class HomeActivity : AppCompatActivity() {
    private lateinit var userPref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        userPref = UserPreference(this)
        val user = userPref.getUser()
        val userInfo = findViewById<TextView>(R.id.userInfo)
        if (user != null) {
            userInfo.text = "Username: ${user.username}\nEmail: ${user.email}"
        } else {
            userInfo.text = "User data not found."
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.action_logout) {
                userPref.clearUser()
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                true
            } else {
                false
            }
        }

        // Add click listener to profile card
        findViewById<View>(R.id.profileCard).setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Add click listener to first card
        findViewById<View>(R.id.card1).setOnClickListener {
            val intent = Intent(this, CrochaPatternActivity::class.java)
            startActivity(intent)
        }
        
        // Add click listener to second card
        findViewById<View>(R.id.card2).setOnClickListener {
            val intent = Intent(this, CrochaTrackActivity::class.java)
            startActivity(intent)
        }
        
        // Add click listener to third card
        findViewById<View>(R.id.card3).setOnClickListener {
            val intent = Intent(this, CrochaStitchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
} 