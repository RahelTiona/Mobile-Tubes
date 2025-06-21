package com.example.crocha

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HakpenAdapter(private val context: Context, private var hakpenList: List<Hakpen>) :
    RecyclerView.Adapter<HakpenAdapter.HakpenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HakpenViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_hakpen, parent, false)
        return HakpenViewHolder(view)
    }

    override fun onBindViewHolder(holder: HakpenViewHolder, position: Int) {
        val hakpen = hakpenList[position]
        holder.name.text = hakpen.name
        holder.texture.text = "Tekstur: ${hakpen.texture}"
        holder.ukuran.text = "Ukuran Umum: ${hakpen.ukuran_umum}"
        holder.project.text = "Rekomendasi Proyek: ${hakpen.project}"

        val imageId = context.resources.getIdentifier(hakpen.image, "drawable", context.packageName)
        if (imageId != 0) {
            holder.image.setImageResource(imageId)
        }
    }

    override fun getItemCount(): Int = hakpenList.size

    fun updateData(newHakpenList: List<Hakpen>) {
        hakpenList = newHakpenList
        notifyDataSetChanged()
    }

    class HakpenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_hakpen)
        val name: TextView = itemView.findViewById(R.id.name_hakpen)
        val texture: TextView = itemView.findViewById(R.id.texture_hakpen)
        val ukuran: TextView = itemView.findViewById(R.id.ukuran_hakpen)
        val project: TextView = itemView.findViewById(R.id.project_hakpen)
    }
} 