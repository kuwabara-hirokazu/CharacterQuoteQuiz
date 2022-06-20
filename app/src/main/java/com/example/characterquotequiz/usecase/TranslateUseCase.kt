package com.example.characterquotequiz.usecase

import com.example.characterquotequiz.data.repository.TranslateRepository
import com.example.characterquotequiz.ui.model.Quiz
import javax.inject.Inject

class TranslateUseCase @Inject constructor(
    private val repository: TranslateRepository
) {
    suspend fun translate(quizList: List<Quiz>, targetIndex: Int): List<Quiz> {
        val translationResult = repository.translate(
            text = quizList[targetIndex].quote,
            targetLang = JAPAN_LANG
        ).translations.first()

        return quizList.mapIndexed { index, quiz ->
            if (index == targetIndex) {
                Quiz(
                    id = quiz.id,
                    character = quiz.character,
                    quote = quiz.quote,
                    translateQuote = translationResult.text,
                    characterUrl = quiz.characterUrl
                )
            } else {
                quiz
            }
        }
    }

    companion object {
        private const val JAPAN_LANG = "JA"
    }
}