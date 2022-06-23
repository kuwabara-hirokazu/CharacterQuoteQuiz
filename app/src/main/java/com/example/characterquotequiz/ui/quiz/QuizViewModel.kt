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
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCase: QuizUseCase,
    private val translateUseCase: TranslateUseCase
) : ViewModel() {

    private val _quizList = MutableLiveData<List<Quiz>>()
    val quizList: LiveData<List<Quiz>> = _quizList

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

    private var startPosition: Int = 10

    fun getQuizList() {
        val quizList = quizList.value ?: listOf()
        startPosition += quizList.size
        viewModelScope.launch {
            try {
                _quizList.value = useCase.getQuotesByAnime("One Piece", startPosition, quizList)
            } catch (error: Exception) {
                if (error is HttpException && error.code() == 404) {
                    _errorMessage.value = R.string.fetch_error_404
                }
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