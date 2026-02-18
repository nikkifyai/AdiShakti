package com.ex.safe.adishakti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.safe.adishakti.databinding.ActivityEvidenceBinding
import io.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class EvidenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEvidenceBinding
    private lateinit var evidenceAdapter: EvidenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvidenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadEvidence()
    }

    private fun setupRecyclerView() {
        evidenceAdapter = EvidenceAdapter(mutableListOf())
        binding.evidenceRecyclerView.apply {
            adapter = evidenceAdapter
            layoutManager = LinearLayoutManager(this@EvidenceActivity)
        }
    }

    private fun loadEvidence() {
        lifecycleScope.launch {
            try {
                val files = SupabaseClient.storage
                    .from("evidence")
                    .list()

                evidenceAdapter.evidenceList.clear()
                evidenceAdapter.evidenceList.addAll(files)
                evidenceAdapter.notifyDataSetChanged()

            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
