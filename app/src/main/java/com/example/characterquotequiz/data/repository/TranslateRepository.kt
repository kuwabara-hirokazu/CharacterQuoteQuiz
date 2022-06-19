package com.example.characterquotequiz.data.repository

import com.example.characterquotequiz.data.remote.DeepLApi
import javax.inject.Inject

class TranslateRepository @Inject constructor(
    private val api: DeepLApi
) {

    suspend fun translate(text: String, targetLang: String) =
        api.translate(text = text, targetLang = targetLang)
}