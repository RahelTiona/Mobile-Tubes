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

class StitchMarkerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StitchMarkerAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stitch_marker, container, false)
        recyclerView = view.findViewById(R.id.stitchMarkerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = StitchMarkerAdapter(requireContext(), emptyList())
        recyclerView.adapter = adapter

        fetchStitchMarkerData()

        return view
    }

    private fun fetchStitchMarkerData() {
        db.collection("materials").whereEqualTo("type", "stitch_marker")
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val markerList = documents.toObjects(StitchMarker::class.java)
                    adapter.updateData(markerList)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("StitchMarkerFragment", "Error getting documents: ", exception)
            }
    }
} 