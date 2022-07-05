package com.example.characterquotequiz.ui.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterquotequiz.R
import com.example.characterquotequiz.ui.model.Quiz
import com.example.characterquotequiz.usecase.QuizUseCase
import com.example.characterquotequiz.usecase.TranslateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCase: QuizUseCase,
    private val translateUseCase: TranslateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizScreenState())
    val uiState: StateFlow<QuizScreenState> = _uiState.asStateFlow()

    private var startPosition: Int = 0

    fun getQuizList() {
        val quizList = uiState.value.quizList
        startPosition += quizList.size
        viewModelScope.launch {
            try {
                val result = useCase.getQuotesByAnime("One Piece", startPosition, quizList)
                _uiState.update { QuizScreenState(result) }
            } catch (error: Exception) {
                val errorMessage = if (error is HttpException && error.code() == 404) {
                    R.string.fetch_error_404
                } else {
                    R.string.unknown_error
                }
                _uiState.update { QuizScreenState(error = errorMessage) }
                Log.e(QuizViewModel::class.java.name, "Fetch error: ${error.message}", error)
            }
        }
    }

    fun translate(index: Int) {
        viewModelScope.launch {
            try {
                val result = translateUseCase.translate(uiState.value.quizList, index)
                _uiState.update { QuizScreenState(result) }
            } catch (error: Exception) {
                _uiState.update { QuizScreenState(error = R.string.unknown_error) }
                Log.e(QuizViewModel::class.java.name, "Translate error: ${error.message}", error)
            }
        }
    }
}