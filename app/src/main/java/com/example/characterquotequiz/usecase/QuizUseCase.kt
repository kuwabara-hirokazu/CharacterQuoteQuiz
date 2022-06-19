package com.example.characterquotequiz.usecase

import com.example.characterquotequiz.data.repository.QuizRepository
import com.example.characterquotequiz.ui.model.Quiz
import javax.inject.Inject

class QuizUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend fun getQuotesByAnime(title: String, page: Int): List<Quiz> {
        return repository.getQuotesByAnime(title, page).mapIndexed { index, response ->
            Quiz(
                id = page + index + 1,
                character = response.character,
                quote = response.quote,
                characterUrl = "$GOOGLE_SEARCH_URL&q=$title+${response.character}"
            )
        }
    }

    companion object {
        private const val GOOGLE_SEARCH_URL = "https://www.google.com/search?tbm=isch"
    }
}