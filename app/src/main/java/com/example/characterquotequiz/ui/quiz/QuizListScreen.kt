package com.example.characterquotequiz.ui.quiz

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun QuizListScreen(viewModel: QuizViewModel) {
    val quizList by viewModel.quizList.observeAsState(listOf())

    LazyColumn() {
        items(quizList.size) { index ->
            QuizItem(quizList[index]) { viewModel.translate(index) }
        }
        // Todo ハンドリング
        item { if (quizList.isNotEmpty()) LoadingIndicator { viewModel.getQuizList() } }
    }
}

@Composable
fun LoadingIndicator(loadPage: () -> Unit) {
    CircularProgressIndicator()

    LaunchedEffect(key1 = true) {
        loadPage()
    }
}