package com.example.characterquotequiz.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.characterquotequiz.data.entity.QuizResponse
import com.example.characterquotequiz.ui.theme.CharacterQuoteQuizTheme

@Composable
fun QuizListScreen(viewModel: QuizViewModel) {
    val quizList by viewModel.quizList.observeAsState(listOf())

    LazyColumn() {
        items(quizList.size) { index ->
            QuizItem(quizList[index], index)
        }
    }
}

@Composable
fun QuizItem(quiz: QuizResponse, index: Int) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Row {
            Text(text = "Q${index + 1}.")
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = quiz.quote)
        }
        Spacer(modifier = Modifier.height(12.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(text = "Ans.", modifier = Modifier.fillMaxWidth())
            Surface(
                modifier = Modifier
                    .animateContentSize()
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                if (isExpanded) Text(text = quiz.character, color = surfaceColor)
            }
        }
    }
}

@Preview
@Composable
fun QuizItemPreview() {
    CharacterQuoteQuizTheme {
        QuizItem(QuizResponse("Zoro", "Who am I?"), 0)
    }
}