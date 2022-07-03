package com.example.characterquotequiz.ui.quiz

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterquotequiz.ActionResult
import com.example.characterquotequiz.asResult
import com.example.characterquotequiz.ui.model.Quiz
import com.example.characterquotequiz.usecase.QuizUseCase
import com.example.characterquotequiz.usecase.TranslateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCase: QuizUseCase,
    private val translateUseCase: TranslateUseCase
) : ViewModel() {

    var quizList = mutableStateListOf<Quiz>()
        private set

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

    private var startPosition: Int = 0

    private val quizStream: Flow<ActionResult<List<Quiz>>> =
        useCase.getQuotesByAnime("One Piece", startPosition, listOf()).asResult()

    val uiState: StateFlow<QuizScreenState> =
        quizStream.map { result ->
            val quizState = when (result) {
                is ActionResult.Success -> {
                    QuizUiState.Success(result.data)
                }
                is ActionResult.Loading -> {
                    QuizUiState.Loading
                }
                is ActionResult.Error -> {
                    Log.e(
                        QuizViewModel::class.java.name,
                        "Fetch error: ${result.exception?.message}",
                        result.exception
                    )
                    QuizUiState.Error
                }
            }
            QuizScreenState(quizState)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = QuizScreenState(quizState = QuizUiState.Loading)
        )

    fun pagingQuizList(quizList: List<Quiz>) {
        startPosition += quizList.size
    }

//    fun getQuizList() {
//        startPosition += quizList.size
//        viewModelScope.launch {
//            try {
//                useCase.getQuotesByAnime("One Piece", startPosition, quizList)
//                    .stateIn(viewModelScope)
//            } catch (error: Exception) {
//                if (error is HttpException && error.code() == 404) {
//                    _errorMessage.value = R.string.fetch_error_404
//                }
//                Log.e(QuizViewModel::class.java.name, "Fetch error: ${error.message}", error)
//            }
//        }
//    }

    fun translate(index: Int) {
        viewModelScope.launch {
            try {
                quizList.addAll(translateUseCase.translate(quizList, index))
            } catch (error: Exception) {
                Log.e(QuizViewModel::class.java.name, "Translate error: ${error.message}", error)
            }
        }
    }
}

sealed interface QuizUiState {
    data class Success(val quizList: List<Quiz>) : QuizUiState
    object Error : QuizUiState
    object Loading : QuizUiState
}

data class QuizScreenState(
    val quizState: QuizUiState
)