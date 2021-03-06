package com.example.characterquotequiz.ui.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.characterquotequiz.R
import com.example.characterquotequiz.ui.TopBar
import com.example.characterquotequiz.ui.navigation.NavigationDestination

@Composable
fun QuizListScreen(viewModel: QuizViewModel, navController: NavController, showError: (Int) -> Unit) {
    val quizState by viewModel.uiState.collectAsState()
    Column {
        TopBar(title = R.string.title_anime_quiz)
        LazyColumn {
            // keyを設定しないとquizのある要素が削除されるとquizListの全てがRecomposeされてしまう
            items(
                items = quizState.quizList,
                key = { quiz ->
                    quiz.id
                }
            ) { quiz ->
                QuizItem(
                    quiz,
                    onQuoteTranslate = { viewModel.translate(quiz.id) },
                    navigateToCharacterImage = {
                        navController.navigate("${NavigationDestination.route}/$it")
                    }
                )
            }
            item {
                if (quizState.quizList.isNotEmpty() && quizState.error == null) LoadingIndicator { viewModel.getQuizList() }
            }
        }
        quizState.error?.let {
            showError(it)
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