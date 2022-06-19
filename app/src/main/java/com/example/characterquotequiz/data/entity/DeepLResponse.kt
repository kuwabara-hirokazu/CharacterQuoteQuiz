package com.example.characterquotequiz.data.entity

data class DeepLResponse(
    val translations: List<Translations>
)

data class Translations(
    val text: String
)