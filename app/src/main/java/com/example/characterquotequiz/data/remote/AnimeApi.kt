package com.example.characterquotequiz.data.remote

import com.example.characterquotequiz.data.entity.QuizResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApi {

    @GET("quotes/anime")
    suspend fun getQuotesByAnime(
        @Query("title") title: String,
        @Query("page") page: Int,
    ): List<QuizResponse>
}