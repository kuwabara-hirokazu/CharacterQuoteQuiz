package com.example.characterquotequiz.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterquotequiz.data.entity.QuizResponse
import com.example.characterquotequiz.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    private val _quizList = MutableLiveData<List<QuizResponse>>()
    val quizList: LiveData<List<QuizResponse>> = _quizList

    fun getQuizList() {
        viewModelScope.launch {
            _quizList.value = repository.getQuotesByAnime()
        }
    }
}