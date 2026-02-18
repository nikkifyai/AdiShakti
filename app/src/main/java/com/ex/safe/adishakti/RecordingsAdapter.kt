package com.ex.safe.adishakti

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class RecordingsAdapter(private val context: Context, var recordings: MutableList<Recording>) : RecyclerView.Adapter<RecordingsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recording, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recording = recordings[position]
        holder.recordingName.text = recording.name
        holder.itemView.setOnClickListener {
            val file = File(recording.path)
            val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "video/mp4")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = recordings.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recordingName: TextView = itemView.findViewById(R.id.recording_name)
    }
}
