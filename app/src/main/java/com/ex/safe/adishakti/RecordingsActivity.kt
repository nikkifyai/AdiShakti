package com.ex.safe.adishakti

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.safe.adishakti.databinding.ActivityRecordingsBinding
import java.io.File

class RecordingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordingsBinding
    private lateinit var recordingsAdapter: RecordingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadRecordings()
    }

    private fun setupRecyclerView() {
        recordingsAdapter = RecordingsAdapter(this, mutableListOf())
        binding.recordingsRecyclerView.apply {
            adapter = recordingsAdapter
            layoutManager = LinearLayoutManager(this@RecordingsActivity)
        }
    }

    private fun loadRecordings() {
        val recordingsDir = getExternalFilesDir(null)
        if (recordingsDir != null) {
            val files = recordingsDir.listFiles { _, name -> name.endsWith(".mp4") }?.map { Recording(it.name, it.absolutePath) }?.toMutableList()
            if (files.isNullOrEmpty()) {
                binding.noRecordingsText.visibility = View.VISIBLE
                binding.recordingsRecyclerView.visibility = View.GONE
            } else {
                recordingsAdapter.recordings = files
                recordingsAdapter.notifyDataSetChanged()
                binding.noRecordingsText.visibility = View.GONE
                binding.recordingsRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recordings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_recordings -> {
                deleteAllRecordings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllRecordings() {
        val recordingsDir = getExternalFilesDir(null)
        if (recordingsDir != null) {
            recordingsDir.listFiles { _, name -> name.endsWith(".mp4") }?.forEach { it.delete() }
            loadRecordings()
        }
    }
}

data class Recording(val name: String, val path: String)
