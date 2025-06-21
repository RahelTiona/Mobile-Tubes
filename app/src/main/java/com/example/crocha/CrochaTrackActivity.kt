package com.example.crocha

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.roundToInt

class CrochaTrackActivity : AppCompatActivity() {

    private lateinit var userPref: UserPreference
    private val db = FirebaseFirestore.getInstance()
    private lateinit var historyContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crocha_track)

        userPref = UserPreference(this)
        historyContainer = findViewById(R.id.historyContainer)

        fetchUserData()
    }

    private fun fetchUserData() {
        val email = userPref.getEmail()
        if (email == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("users").whereEqualTo("email", email).limit(1).get()
            .addOnSuccessListener { userDocs ->
                if (!userDocs.isEmpty) {
                    val history = userDocs.documents[0].get("history") as? Map<String, Long>
                    if (history != null) {
                        fetchPatternsAndDisplayHistory(history)
                    }
                }
            }
    }

    private fun fetchPatternsAndDisplayHistory(history: Map<String, Long>) {
        db.collection("pattern").get()
            .addOnSuccessListener { patternDocs ->
                val inflater = LayoutInflater.from(this)
                historyContainer.removeAllViews() // Clear previous views
                for ((patternKey, currentStep) in history) {
                    val patternDoc = patternDocs.find { it.id == patternKey || it.getString("image") == patternKey }
                    if (patternDoc != null) {
                        val patternName = patternDoc.getString("name") ?: "Unknown Pattern"
                        val steps = patternDoc.get("steps") as? List<String> ?: emptyList()
                        val totalSteps = steps.size
                        
                        if (totalSteps > 0) {
                            val progress = ((currentStep + 1).toFloat() / totalSteps * 100).roundToInt()
                            val historyView = inflater.inflate(R.layout.item_history, historyContainer, false) as CardView
                            
                            historyView.findViewById<TextView>(R.id.patternName).text = patternName
                            historyView.findViewById<ProgressBar>(R.id.progressBar).progress = progress
                            
                            historyView.setOnClickListener {
                                val intent = Intent(this, PatternTutorialActivity::class.java)
                                intent.putExtra("image", patternDoc.getString("image") ?: "")
                                intent.putExtra("name", patternName)
                                intent.putStringArrayListExtra("materials", ArrayList(patternDoc.get("materials") as? List<String> ?: emptyList()))
                                intent.putStringArrayListExtra("steps", ArrayList(steps))
                                startActivity(intent)
                            }
                            
                            historyContainer.addView(historyView)
                        }
                    }
                }
            }
    }
} 