package com.example.characterquotequiz.usecase

import com.example.characterquotequiz.data.repository.QuizRepository
import com.example.characterquotequiz.ui.model.Quiz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuizUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    fun getQuotesByAnime(title: String, startPosition: Int, quizList: List<Quiz>): Flow<List<Quiz>> {
        return repository.getQuotesByAnime(title, startPosition).map {
            quizList + it.mapIndexed { index, response ->
                Quiz(
                    id = quizList.size + index + 1,
                    character = response.character,
                    quote = response.quote,
                    translateQuote = null,
                    characterUrl = "$GOOGLE_SEARCH_URL&q=$title+${response.character}"
                )
            }
        }
    }

    companion object {
        private const val GOOGLE_SEARCH_URL = "https://www.google.com/search?tbm=isch"
    }
}