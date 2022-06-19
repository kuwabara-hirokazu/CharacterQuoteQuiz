package com.example.characterquotequiz.usecase

import com.example.characterquotequiz.data.entity.DeepLResponse
import com.example.characterquotequiz.data.repository.TranslateRepository
import javax.inject.Inject

class TranslateUseCase @Inject constructor(
    private val repository: TranslateRepository
) {
    suspend fun translate(text: String): DeepLResponse {
        return repository.translate(
            text = text,
            targetLang = JAPAN_LANG
        )
    }

    companion object {
        private const val JAPAN_LANG = "JA"
    }
}