package com.example.crocha

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore

class PatternTutorialActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var userPref: UserPreference
    private var currentStep = -1
    private lateinit var historyKey: String
    private lateinit var userEmail: String
    private val stepViews = mutableListOf<CardView>()
    private lateinit var stepsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pattern_tutorial)

        userPref = UserPreference(this)
        userEmail = userPref.getEmail() ?: ""

        val imageName = intent.getStringExtra("image") ?: "crocha_logo"
        historyKey = imageName // gunakan imageName sebagai key history
        val name = intent.getStringExtra("name") ?: ""
        val materials = intent.getStringArrayListExtra("materials") ?: arrayListOf()
        val steps = intent.getStringArrayListExtra("steps") ?: arrayListOf()

        // Setup UI
        findViewById<TextView>(R.id.patternName).text = name
        val patternImage = findViewById<ImageView>(R.id.patternImage)
        val resId = resources.getIdentifier(imageName, "drawable", packageName)
        patternImage.setImageResource(if (resId != 0) resId else R.drawable.crocha_logo)
        
        setupMaterials(materials)
        setupTerms()
        setupInitialSteps(steps)

        if (userEmail.isNotEmpty() && historyKey.isNotEmpty()) {
            fetchUserHistory()
        }
    }
    
    private fun setupMaterials(materials: List<String>) {
        val materialsContainer = findViewById<LinearLayout>(R.id.materialsContainer)
        materials.forEach {
            val tv = TextView(this)
            tv.text = "• $it"
            tv.textSize = 15f
            tv.setPadding(0, 0, 0, 8)
            materialsContainer.addView(tv)
        }
    }

    private fun setupTerms() {
        val terms = listOf("SC : single crochet", "INC : increase", "DEC : decrease", "MR : Magic Ring")
        val termsContainer = findViewById<LinearLayout>(R.id.termsContainer)
        terms.forEach {
            val tv = TextView(this)
            tv.text = "• $it"
            tv.textSize = 15f
            tv.setPadding(0, 0, 0, 8)
            termsContainer.addView(tv)
        }
    }

    private fun setupInitialSteps(steps: List<String>) {
        stepsContainer = findViewById(R.id.stepsContainer)
        val inflater = LayoutInflater.from(this)
        steps.forEachIndexed { index, step ->
            val card = inflater.inflate(R.layout.item_step, stepsContainer, false) as CardView
            val tv = card.findViewById<TextView>(R.id.stepText)
            tv.text = "${index + 1}. $step"
            card.setOnClickListener {
                handleStepClick(index)
            }
            stepsContainer.addView(card)
            stepViews.add(card)
        }
    }

    private fun fetchUserHistory() {
        db.collection("users").whereEqualTo("email", userEmail).limit(1).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents[0]
                    val history = userDoc.get("history") as? Map<*, *>
                    currentStep = (history?.get(historyKey) as? Long)?.toInt() ?: -1
                    updateStepStyles()
                }
            }
    }
    
    private fun handleStepClick(index: Int) {
        if (userEmail.isEmpty() || historyKey.isEmpty()) return

        currentStep = if (currentStep == index) -1 else index // Toggle off if same step is clicked
        
        updateUserHistory()
        updateStepStyles()
    }

    private fun updateUserHistory() {
        db.collection("users").whereEqualTo("email", userEmail).limit(1).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val docId = documents.documents[0].id
                    db.collection("users").document(docId)
                        .update("history.$historyKey", currentStep)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update history: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateStepStyles() {
        stepViews.forEachIndexed { index, card ->
            if (index <= currentStep) {
                card.setBackgroundResource(R.drawable.bg_step_selected)
            } else {
                card.setBackgroundResource(R.drawable.bg_grid_item)
            }
        }
    }
} 