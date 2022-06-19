package com.example.characterquotequiz.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterquotequiz.ui.model.Quiz
import com.example.characterquotequiz.usecase.QuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCase: QuizUseCase
) : ViewModel() {

    private val _quizList = MutableLiveData<List<Quiz>>()
    val quizList: LiveData<List<Quiz>> = _quizList

    private var page: Int = 0

    fun getQuizList() {
        viewModelScope.launch {
            try {
                _quizList.value = useCase.getQuotesByAnime("One Piece", page)
                page += 10
            } catch (error: Exception) {
                Log.e(QuizViewModel::class.java.name, "Fetch error: ${error.message}", error)
            }
        }
    }
}