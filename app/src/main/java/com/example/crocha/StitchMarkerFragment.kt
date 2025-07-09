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

    // > Deklarasi variabel RecyclerView dan Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StitchMarkerAdapter

    // > Inisialisasi Firestore
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // > Inflate layout XML fragment_stitch_marker
        val view = inflater.inflate(R.layout.fragment_stitch_marker, container, false)

        // > Cari RecyclerView dari layout XML
        recyclerView = view.findViewById(R.id.stitchMarkerRecyclerView)

        // > Set layout manager agar RecyclerView menampilkan list secara vertikal
        recyclerView.layoutManager = LinearLayoutManager(context)

        // > Buat adapter kosong terlebih dahulu
        adapter = StitchMarkerAdapter(requireContext(), emptyList())

        // > Sambungkan adapter ke RecyclerView
        recyclerView.adapter = adapter

        // > Ambil data dari Firestore
        fetchStitchMarkerData()

        // > Kembalikan view agar fragment bisa ditampilkan
        return view
    }

    private fun fetchStitchMarkerData() {
        // > Query Firestore ke koleksi 'materials' untuk dokumen dengan 'type = stitch_marker'
        db.collection("materials").whereEqualTo("type", "stitch_marker")
            .get()
            .addOnSuccessListener { documents ->
                // > Jika dokumen ditemukan dan tidak kosong
                if (!documents.isEmpty) {
                    // > Ubah dokumen ke list data class StitchMarker
                    val markerList = documents.toObjects(StitchMarker::class.java)
                    // > Update adapter dengan data baru
                    adapter.updateData(markerList)
                }
            }
            .addOnFailureListener { exception ->
                // > Log error jika gagal ambil data
                Log.w("StitchMarkerFragment", "Error getting documents: ", exception)
            }
    }
}



//HakpenFragment (ambil data dari Firestore)
//↓
//Adapter (HakpenAdapter menerima data)
//↓
//RecyclerView (mengatur daftar item)
//↓
//item_hakpen.xml (tampilan tiap hakpen ditampilkan lewat ViewHolder)

