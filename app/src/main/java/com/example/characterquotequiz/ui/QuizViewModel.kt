package com.example.characterquotequiz.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.characterquotequiz.data.Quiz
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor() : ViewModel() {

    private val _quizList = MutableLiveData<List<Quiz>>()
    val quizList: LiveData<List<Quiz>> = _quizList

    fun getQuizList() {
        _quizList.value = listOf(
            Quiz("Who am I?", "Taro"),
            Quiz("Who am I?", "Jiro"),
            Quiz("Who am I?", "Saburo"),
        )
    }
}