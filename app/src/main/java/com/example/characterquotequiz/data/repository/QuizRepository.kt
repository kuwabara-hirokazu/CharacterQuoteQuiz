package com.example.characterquotequiz.data.repository

import com.example.characterquotequiz.data.remote.AnimeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val api: AnimeApi
) {
    fun getQuotesByAnime(title: String, page: Int) = flow {
        emit(api.getQuotesByAnime(title, page))
    }.flowOn(Dispatchers.IO)
}