package com.example.characterquotequiz.ui.quiz

import com.example.characterquotequiz.ui.model.Quiz

data class QuizScreenState(
    val quizList: List<Quiz> = listOf(),
    val error: Int? = null
)