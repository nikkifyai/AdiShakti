package com.ex.safe.adishakti.util

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import java.io.File

object SupabaseHelper {

    // 🔥 PUT YOUR REAL VALUES HERE
    private const val SUPABASE_URL =
        "https://tammravupbrbitzfbelo.supabase.co"

    private const val SUPABASE_ANON_KEY =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRhbW1yYXZ1cGJyYml0emZiZWxvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzA4MjQxMDUsImV4cCI6MjA4NjQwMDEwNX0.16R65Qbl2EokRAntqXzY8jW1ebz22x0d8BagBE3PQ94"

    // 🔥 YOUR REAL BUCKET NAME
    private const val BUCKET_NAME =
        "incident-media"

    private val client = HttpClient(Android)

    fun uploadFile(file: File, callback: (String?) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val fileBytes = file.readBytes()
                val fileName = file.name

                val uploadUrl =
                    "$SUPABASE_URL/storage/v1/object/$BUCKET_NAME/$fileName"

                Log.d("SupabaseUpload", "Uploading to: $uploadUrl")

                val response: HttpResponse =
                    client.post(uploadUrl) {

                        header("apikey", SUPABASE_ANON_KEY)
                        header("Authorization", "Bearer $SUPABASE_ANON_KEY")

                        contentType(ContentType.Application.OctetStream)

                        setBody(fileBytes)
                    }

                Log.d("SupabaseUpload", "Status: ${response.status}")

                if (response.status == HttpStatusCode.OK ||
                    response.status == HttpStatusCode.Created
                ) {

                    val publicUrl =
                        "$SUPABASE_URL/storage/v1/object/public/$BUCKET_NAME/$fileName"

                    withContext(Dispatchers.Main) {
                        callback(publicUrl)
                    }

                } else {

                    withContext(Dispatchers.Main) {
                        callback(null)
                    }
                }

            } catch (e: Exception) {

                Log.e("SupabaseUpload", "Error: ${e.message}")
                e.printStackTrace()

                withContext(Dispatchers.Main) {
                    callback(null)
                }
            }
        }
    }
}
