package com.example.crocha

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileActivity : AppCompatActivity() {
    private lateinit var usernameInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private val db = FirebaseFirestore.getInstance()
    private lateinit var userPref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        userPref = UserPreference(this)
        
        // Initialize views
        usernameInput = findViewById(R.id.usernameInput)
        phoneInput = findViewById(R.id.phoneInput)
        emailInput = findViewById(R.id.emailInput)

        // Load current user data
        loadUserData()

        // Set up save button click listener
        findViewById<android.widget.Button>(R.id.saveButton).setOnClickListener {
            saveProfileChanges()
        }
    }

    private fun loadUserData() {
        val user = userPref.getUser()
        if (user != null) {
            usernameInput.setText(user.username)
            phoneInput.setText(user.phone)
            emailInput.setText(user.email)
        }
    }

    private fun saveProfileChanges() {
        val username = usernameInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val email = emailInput.text.toString().trim()

        // Basic validation
        if (username.isEmpty()) {
            usernameInput.error = "Username is required"
            return
        }

        if (phone.isEmpty()) {
            phoneInput.error = "Phone number is required"
            return
        }

        if (email.isEmpty()) {
            emailInput.error = "Email is required"
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Please enter a valid email"
            return
        }

        // Get current user email for Firestore query
        val currentUser = userPref.getUser()
        if (currentUser == null) {
            Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
            return
        }

        // Update in Firestore
        db.collection("users")
            .whereEqualTo("email", currentUser.email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val docId = documents.documents[0].id
                    val updates = hashMapOf(
                        "username" to username,
                        "phone" to phone,
                        "email" to email
                    )
                    
                    db.collection("users").document(docId)
                        .update(updates as Map<String, Any>)
                        .addOnSuccessListener {
                            // Update local preferences
                            userPref.saveUser(username, phone, email)
                            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "User not found in database", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
} 