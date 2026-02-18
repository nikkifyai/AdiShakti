package com.ex.safe.adishakti.util

import android.content.Context
import android.location.Location
import android.os.Build
import android.telephony.SmsManager
import com.google.android.gms.location.LocationServices

object EmergencyHelper {

    fun sendEmergencySMS(context: Context, phone: String?) {

        if (phone.isNullOrEmpty()) return

        if (!PermissionHelper.hasSMSPermission(context)) return

        if (!PermissionHelper.hasLocationPermission(context)) {
            sendSMS(context, phone, "EMERGENCY! Location permission denied.")
            return
        }

        try {
            val fusedLocation =
                LocationServices.getFusedLocationProviderClient(context)

            fusedLocation.lastLocation
                .addOnSuccessListener { location: Location? ->

                    val message = buildMessage(location)

                    sendSMS(context, phone, message)
                }
                .addOnFailureListener {
                    sendSMS(context, phone, "EMERGENCY! Unable to fetch location.")
                }

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun buildMessage(location: Location?): String {

        return if (location != null) {
            "🚨 EMERGENCY!\n" +
                    "My current location:\n" +
                    "https://maps.google.com/?q=${location.latitude},${location.longitude}"
        } else {
            "🚨 EMERGENCY!\nLocation unavailable."
        }
    }

    private fun sendSMS(context: Context, phone: String, message: String) {

        try {

            val smsManager =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    context.getSystemService(SmsManager::class.java)
                } else {
                    SmsManager.getDefault()
                }

            smsManager?.sendTextMessage(
                phone,
                null,
                message,
                null,
                null
            )

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
