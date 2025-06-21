package com.example.crocha

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CrochaPatternAdapter(
    private val context: Context,
    private val patterns: List<Map<String, Any>>
) : RecyclerView.Adapter<CrochaPatternAdapter.PatternViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatternViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pattern, parent, false)
        return PatternViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatternViewHolder, position: Int) {
        val pattern = patterns[position]
        val name = pattern["name"] as? String ?: ""
        val imageName = pattern["image"] as? String ?: ""
        val patternId = pattern["id"] as? String ?: ""

        holder.patternName.text = name
        // Ambil resource id dari nama gambar
        val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        if (resId != 0) {
            holder.patternImage.setImageResource(resId)
        } else {
            holder.patternImage.setImageResource(R.drawable.crocha_logo)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PatternTutorialActivity::class.java)
            intent.putExtra("image", imageName)
            intent.putExtra("name", name)
            intent.putExtra("patternId", patternId)
            val materials = (pattern["materials"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
            val steps = (pattern["steps"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
            intent.putStringArrayListExtra("materials", ArrayList(materials))
            intent.putStringArrayListExtra("steps", ArrayList(steps))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = patterns.size

    class PatternViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val patternImage: ImageView = itemView.findViewById(R.id.patternImage)
        val patternName: TextView = itemView.findViewById(R.id.patternName)
    }
} 