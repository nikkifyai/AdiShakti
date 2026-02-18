package com.ex.safe.adishakti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.safe.adishakti.databinding.ActivityTrustedContactsBinding

class TrustedContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrustedContactsBinding
    private lateinit var trustedContactsAdapter: TrustedContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrustedContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadTrustedContacts()

        binding.addTrustedContactButton.setOnClickListener {
            // Implement logic to add a new trusted contact
        }
    }

    private fun setupRecyclerView() {
        trustedContactsAdapter = TrustedContactsAdapter(mutableListOf())
        binding.trustedContactsRecyclerView.apply {
            adapter = trustedContactsAdapter
            layoutManager = LinearLayoutManager(this@TrustedContactsActivity)
        }
    }

    private fun loadTrustedContacts() {
        // Implement logic to load trusted contacts from storage
    }
}
