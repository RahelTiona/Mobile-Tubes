package com.example.crocha

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BenangAdapter(private val context: Context, private var benangList: List<Benang>) :
    RecyclerView.Adapter<BenangAdapter.BenangViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BenangViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_benang, parent, false)
        return BenangViewHolder(view)
    }

    override fun onBindViewHolder(holder: BenangViewHolder, position: Int) {
        val benang = benangList[position]
        holder.name.text = benang.name
        holder.texture.text = "Tekstur: ${benang.texture}"
        holder.hakpen.text = "Hakpen: ${benang.hakpen}"
        holder.project.text = "Rekomendasi Proyek: ${benang.project}"

        val imageId = context.resources.getIdentifier(benang.image, "drawable", context.packageName)
        if (imageId != 0) {
            holder.image.setImageResource(imageId)
        }
    }

    override fun getItemCount(): Int = benangList.size

    fun updateData(newBenangList: List<Benang>) {
        benangList = newBenangList
        notifyDataSetChanged()
    }

    class BenangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_benang)
        val name: TextView = itemView.findViewById(R.id.name_benang)
        val texture: TextView = itemView.findViewById(R.id.texture_benang)
        val hakpen: TextView = itemView.findViewById(R.id.hakpen_benang)
        val project: TextView = itemView.findViewById(R.id.project_benang)
    }
} 