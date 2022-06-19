package com.example.characterquotequiz.data.repository

import com.example.characterquotequiz.data.remote.AnimeApi
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val api: AnimeApi
) {
    suspend fun getQuotesByAnime() = api.getQuotesByAnime()
}