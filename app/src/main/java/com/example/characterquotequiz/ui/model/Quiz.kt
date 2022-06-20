package com.example.characterquotequiz.ui.model

data class Quiz(
    val id: Int,
    val character: String,
    val quote: String,
    val translateQuote: String?,
    val characterUrl: String
)