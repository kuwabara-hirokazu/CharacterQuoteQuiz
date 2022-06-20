package com.example.characterquotequiz.data.entity

data class DeepLResponse(
    val translations: List<TranslationResult>
)

data class TranslationResult(
    val text: String
)