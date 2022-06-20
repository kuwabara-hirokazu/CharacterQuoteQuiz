package com.example.characterquotequiz.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterquotequiz.data.entity.TranslationResult
import com.example.characterquotequiz.ui.model.Quiz
import com.example.characterquotequiz.usecase.QuizUseCase
import com.example.characterquotequiz.usecase.TranslateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCase: QuizUseCase,
    private val translateUseCase: TranslateUseCase
) : ViewModel() {

    private val _quizList = MutableLiveData<List<Quiz>>()
    val quizList: LiveData<List<Quiz>> = _quizList

    private val _transitionResult = MutableLiveData<TranslationResult>()
    val transitionResult: LiveData<TranslationResult> = _transitionResult

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

    fun translate(index: Int) {
        val quizList = quizList.value ?: return
        viewModelScope.launch {
            try {
                _quizList.value = translateUseCase.translate(quizList, index)
            } catch (error: Exception) {
                Log.e(QuizViewModel::class.java.name, "Translate error: ${error.message}", error)
            }
        }
    }
}