package com.example.characterquotequiz.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.characterquotequiz.ui.model.Quiz

@Composable
fun QuizListScreen(viewModel: QuizViewModel) {
    val quizList by viewModel.quizList.observeAsState(listOf())

    LazyColumn() {
        items(quizList.size) { index ->
            QuizItem(quizList[index]) { viewModel.translate(index) }
        }
        // Todo ハンドリング
        item { if (quizList.isNotEmpty()) LoadingIndicator(viewModel) }
    }
}

@Composable
fun QuizItem(quiz: Quiz, onQuoteTranslate: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row {
            Text(text = "Q${quiz.id}.", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = quiz.quote)
        }
        Spacer(modifier = Modifier.height(12.dp))

        var isExpanded by remember { mutableStateOf(false) }

        Column(Modifier.padding(horizontal = 8.dp)) {
            Row {
                Button(
                    enabled = quiz.translateQuote == null,
                    onClick = { onQuoteTranslate(quiz.quote) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White,
                        disabledBackgroundColor = Color.Gray,
                        disabledContentColor = Color.White
                    ),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(text = "翻訳")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = { isExpanded = !isExpanded },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.Red,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(text = "Answer")
                }
            }
            Column(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
            ) {
                quiz.translateQuote?.let {
                    Text(
                        text = it, modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
                if (isExpanded) {
                    Text(
                        text = quiz.character,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    CharacterImage(url = quiz.characterUrl)
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator(viewModel: QuizViewModel) {
    CircularProgressIndicator()

    LaunchedEffect(key1 = true) {
        viewModel.getQuizList()
    }
}

@Composable
fun CharacterImage(url: String) {
    AndroidView(
        factory = ::WebView,
        update = { webView ->
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url)
        },
        modifier = Modifier.height(320.dp)
    )
}