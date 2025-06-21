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

class JarumTapestriFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JarumTapestriAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_jarum_tapestri, container, false)
        recyclerView = view.findViewById(R.id.jarumTapestriRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = JarumTapestriAdapter(requireContext(), emptyList())
        recyclerView.adapter = adapter

        fetchJarumTapestriData()

        return view
    }

    private fun fetchJarumTapestriData() {
        db.collection("materials").whereEqualTo("type", "jarum_tapestri")
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val jarumList = documents.toObjects(JarumTapestri::class.java)
                    adapter.updateData(jarumList)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("JarumTapestriFragment", "Error getting documents: ", exception)
            }
    }
} 