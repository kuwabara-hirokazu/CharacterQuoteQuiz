package com.example.characterquotequiz.ui.quiz

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController
import com.example.characterquotequiz.ui.NavigationDestination

@Composable
fun QuizListScreen(viewModel: QuizViewModel, canLoading: Boolean, navController: NavController) {
    val quizList by viewModel.quizList.observeAsState(listOf())

    LazyColumn {
        // keyを設定しないとquizのある要素が削除されるとquizListの全てがRecomposeされてしまう
        items(
            items = quizList,
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
            if (quizList.isNotEmpty() && canLoading) LoadingIndicator { viewModel.getQuizList() }
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