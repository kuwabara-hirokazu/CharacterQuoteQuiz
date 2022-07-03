package com.example.characterquotequiz.ui.quiz

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*

@Composable
fun QuizListScreen(viewModel: QuizViewModel, canLoading: Boolean) {
    val quizState: QuizScreenState by viewModel.uiState.collectAsState()

    when (val state = quizState.quizState) {
        QuizUiState.Loading -> {}
        QuizUiState.Error -> {}
        is QuizUiState.Success -> {
            LazyColumn {
                // keyを設定しないとquizのある要素が削除されるとquizListの全てがRecomposeされてしまう
                items(
                    items = state.quizList,
                    key = { quiz ->
                        quiz.id
                    }
                ) { quiz ->
                    QuizItem(quiz) { viewModel.translate(quiz.id) }
                }
                item {
                    if (state.quizList.isNotEmpty() && canLoading) LoadingIndicator {
                        viewModel.pagingQuizList(state.quizList)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator(loadPage: () -> Unit) {
    CircularProgressIndicator()

    LaunchedEffect(key1 = true) {
        loadPage()
    }
}