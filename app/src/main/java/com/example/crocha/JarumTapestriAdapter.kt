package com.example.crocha

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JarumTapestriAdapter(private val context: Context, private var jarumList: List<JarumTapestri>) :
    RecyclerView.Adapter<JarumTapestriAdapter.JarumTapestriViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JarumTapestriViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_jarum_tapestri, parent, false)
        return JarumTapestriViewHolder(view)
    }

    override fun onBindViewHolder(holder: JarumTapestriViewHolder, position: Int) {
        val jarum = jarumList[position]
        holder.name.text = jarum.name
        holder.texture.text = "Tekstur: ${jarum.texture}"
        holder.ukuran.text = "Ukuran Umum: ${jarum.ukuran_umum}"
        holder.project.text = "Rekomendasi Proyek: ${jarum.project}"

        val imageId = context.resources.getIdentifier(jarum.image, "drawable", context.packageName)
        if (imageId != 0) {
            holder.image.setImageResource(imageId)
        }
    }

    override fun getItemCount(): Int = jarumList.size

    fun updateData(newJarumList: List<JarumTapestri>) {
        jarumList = newJarumList
        notifyDataSetChanged()
    }

    class JarumTapestriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_jarum_tapestri)
        val name: TextView = itemView.findViewById(R.id.name_jarum_tapestri)
        val texture: TextView = itemView.findViewById(R.id.texture_jarum_tapestri)
        val ukuran: TextView = itemView.findViewById(R.id.ukuran_jarum_tapestri)
        val project: TextView = itemView.findViewById(R.id.project_jarum_tapestri)
    }
} 