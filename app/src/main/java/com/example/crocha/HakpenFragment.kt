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

class HakpenFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HakpenAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hakpen, container, false)
        recyclerView = view.findViewById(R.id.hakpenRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HakpenAdapter(requireContext(), emptyList())
        recyclerView.adapter = adapter

        fetchHakpenData()

        return view
    }

    private fun fetchHakpenData() {
        db.collection("materials").whereEqualTo("type", "hakpen")
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val hakpenList = documents.toObjects(Hakpen::class.java)
                    adapter.updateData(hakpenList)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("HakpenFragment", "Error getting documents: ", exception)
            }
    }
} 