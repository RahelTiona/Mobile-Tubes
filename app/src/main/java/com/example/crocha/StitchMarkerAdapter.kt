package com.example.crocha

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// > Adapter untuk menampilkan daftar Stitch Marker di RecyclerView
class StitchMarkerAdapter(private val context: Context, private var markerList: List<StitchMarker>) :
    RecyclerView.Adapter<StitchMarkerAdapter.StitchMarkerViewHolder>() {

    // > Membuat ViewHolder baru dengan layout item_stitch_marker
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StitchMarkerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_stitch_marker, parent, false)
        return StitchMarkerViewHolder(view)
    }

    // > Menghubungkan data ke ViewHolder berdasarkan posisi dalam list
    override fun onBindViewHolder(holder: StitchMarkerViewHolder, position: Int) {
        val marker = markerList[position]
        // > Set nama, tekstur, ukuran, dan proyek dari objek marker
        holder.name.text = marker.name // Tidak cari findViewById lagi!
        holder.texture.text = "Tekstur: ${marker.texture}"
        holder.ukuran.text = "Ukuran Umum: ${marker.ukuran_umum}"
        holder.project.text = "Rekomendasi Proyek: ${marker.project}"

        // > Ambil ID gambar dari drawable berdasarkan nama di marker.image
        val imageId = context.resources.getIdentifier(marker.image, "drawable", context.packageName)
        if (imageId != 0) {
            holder.image.setImageResource(imageId) // > Tampilkan gambar jika ditemukan
        }
    }

    // > Mengembalikan jumlah item dalam list
    override fun getItemCount(): Int = markerList.size

    // > Fungsi untuk memperbarui data dan memberi tahu RecyclerView untuk refresh tampilan
    fun updateData(newMarkerList: List<StitchMarker>) {
        markerList = newMarkerList
        notifyDataSetChanged()
    }

    // > ViewHolder berisi referensi ke komponen UI dari item layout
    class StitchMarkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_stitch_marker)
        val name: TextView = itemView.findViewById(R.id.name_stitch_marker)
        val texture: TextView = itemView.findViewById(R.id.texture_stitch_marker)
        val ukuran: TextView = itemView.findViewById(R.id.ukuran_stitch_marker)
        val project: TextView = itemView.findViewById(R.id.project_stitch_marker)
    }
}
