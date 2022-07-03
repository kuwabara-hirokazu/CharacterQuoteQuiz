package com.example.characterquotequiz.usecase

import com.example.characterquotequiz.data.repository.QuizRepository
import com.example.characterquotequiz.ui.model.Quiz
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class QuizUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend fun getQuotesByAnime(title: String, startPosition: Int, quizList: List<Quiz>): List<Quiz> {
        return quizList + repository.getQuotesByAnime(title, startPosition).mapIndexed { index, response ->
            val characterUrl = "$GOOGLE_SEARCH_URL&q=$title+${response.character}"
            Quiz(
                id = quizList.size + index + 1,
                character = response.character,
                quote = response.quote,
                translateQuote = null,
                characterUrl = URLEncoder.encode(characterUrl, StandardCharsets.UTF_8.toString())   // NavigationでURLを引数に渡すときはエンコードが必要
            )
        }
    }

    companion object {
        private const val GOOGLE_SEARCH_URL = "https://www.google.com/search?tbm=isch"
    }
}