package com.example.characterquotequiz.data.remote

import com.example.characterquotequiz.data.entity.QuizResponse
import retrofit2.http.GET

interface AnimeApi {

    @GET("quotes")
    suspend fun getQuotesByAnime(): List<QuizResponse>
}