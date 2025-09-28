package com.example.a1gworkapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets

class SheetsRepository {

    suspend fun getSheetData(apiKey: String, spreadsheetId: String, range: String): List<List<Any>>? {
        return withContext(Dispatchers.IO) {
            try {
                val sheetsService = Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    null
                )
                    .setApplicationName("1GWorkApp")
                    .build()

                val result = sheetsService.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .setKey(apiKey)
                    .execute()

                result.getValues()
            } catch (e: Exception) {
                Log.e("SheetsRepository", "Ошибка при получении данных: ${e.javaClass.simpleName} - ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }
}