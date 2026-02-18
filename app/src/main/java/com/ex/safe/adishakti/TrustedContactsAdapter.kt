package com.ex.safe.adishakti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class TrustedContact(val name: String, val phone: String)

class TrustedContactsAdapter(private val trustedContacts: MutableList<TrustedContact>) : RecyclerView.Adapter<TrustedContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trusted_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = trustedContacts[position]
        holder.contactName.text = contact.name
        holder.contactPhone.text = contact.phone
    }

    override fun getItemCount() = trustedContacts.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.findViewById(R.id.contact_name)
        val contactPhone: TextView = itemView.findViewById(R.id.contact_phone)
    }
}
