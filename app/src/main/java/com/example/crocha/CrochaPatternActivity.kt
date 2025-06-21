package com.example.crocha

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class CrochaPatternActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private val patterns = mutableListOf<Map<String, Any>>()
    private lateinit var adapter: CrochaPatternAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crocha_pattern)

        recyclerView = findViewById(R.id.patternRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = CrochaPatternAdapter(this, patterns)
        recyclerView.adapter = adapter

        fetchPatternsFromFirestore()
    }

    private fun fetchPatternsFromFirestore() {
        db.collection("pattern")
            .get()
            .addOnSuccessListener { result ->
                patterns.clear()
                for (doc in result) {
                    val data = doc.data
                    data["id"] = doc.id
                    if (data.containsKey("image") && data.containsKey("name")) {
                        patterns.add(data)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load patterns: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
} 