package com.ex.safe.adishakti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ex.safe.adishakti.storage.FileObject

class EvidenceAdapter(val evidenceList: MutableList<FileObject>) : RecyclerView.Adapter<EvidenceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evidence, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val evidence = evidenceList[position]
        holder.evidenceName.text = evidence.name
        holder.evidenceDate.text = evidence.createdAt.toString()
        holder.playButton.setOnClickListener {
            // Implement play video functionality
        }
    }

    override fun getItemCount() = evidenceList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val evidenceName: TextView = itemView.findViewById(R.id.evidence_name)
        val evidenceDate: TextView = itemView.findViewById(R.id.evidence_date)
        val playButton: Button = itemView.findViewById(R.id.play_button)
    }
}
