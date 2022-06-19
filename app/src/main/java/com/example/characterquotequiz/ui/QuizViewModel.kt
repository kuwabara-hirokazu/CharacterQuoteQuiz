package com.example.characterquotequiz.ui

import androidx.lifecycle.ViewModel
import com.example.characterquotequiz.data.Quiz

class QuizViewModel : ViewModel() {

    fun getQuizList(): List<Quiz> {
        return listOf(
            Quiz("Who am I?", "Taro"),
            Quiz("Who am I?", "Jiro"),
            Quiz("Who am I?", "Saburo"),
        )
    }
}