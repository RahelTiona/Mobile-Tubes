package com.example.crocha

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class BenangFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BenangAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_benang, container, false)
        recyclerView = view.findViewById(R.id.benangRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = BenangAdapter(requireContext(), emptyList())
        recyclerView.adapter = adapter
        
        fetchBenangData()
        
        return view
    }

    private fun fetchBenangData() {
        db.collection("materials").whereEqualTo("type", "benang")
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val benangList = documents.toObjects(Benang::class.java)
                    adapter.updateData(benangList)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("BenangFragment", "Error getting documents: ", exception)
            }
    }
} 