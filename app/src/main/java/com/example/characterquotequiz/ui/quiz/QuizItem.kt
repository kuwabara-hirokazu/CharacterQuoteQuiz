package com.example.characterquotequiz.ui.quiz

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.characterquotequiz.R
import com.example.characterquotequiz.ui.model.Quiz

@Composable
fun QuizItem(quiz: Quiz, onQuoteTranslate: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.question, quiz.id.toString()),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = quiz.quote)
        }
        Spacer(modifier = Modifier.height(12.dp))

        var isLoading by remember { mutableStateOf(false) }
        var isExpanded by remember { mutableStateOf(false) }

        Column(Modifier.padding(horizontal = 8.dp)) {
            ReactionButtons(
                quiz = quiz,
                isLoading = isLoading,
                startLoading = { isLoading = true },
                onQuoteTranslate = onQuoteTranslate,
                isExpanded = isExpanded,
                onAnswerClicked = { isExpanded = !it }
            )
            AnswerResult(isLoading, quiz, isExpanded) { isLoading = false }
        }
    }
}

@Composable
fun ReactionButtons(
    quiz: Quiz,
    isLoading: Boolean,
    startLoading: () -> Unit,
    onQuoteTranslate: (String) -> Unit,
    isExpanded: Boolean,
    onAnswerClicked: (Boolean) -> Unit
) {
    Row {
        Button(
            enabled = !isLoading && quiz.translateQuote == null,
            onClick = {
                startLoading()
                onQuoteTranslate(quiz.quote)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                disabledBackgroundColor = Color.Gray,
                disabledContentColor = Color.White
            ),
            modifier = Modifier.width(100.dp)
        ) {
            Text(text = stringResource(R.string.translate))
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            onClick = { onAnswerClicked(isExpanded) },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ),
            modifier = Modifier.width(100.dp)
        ) {
            Text(text = stringResource(R.string.answer))
        }
    }
}

@Composable
fun AnswerResult(
    isLoading: Boolean,
    quiz: Quiz,
    isExpanded: Boolean,
    shownTranslateQuote: () -> Unit
) {
    Column(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            )
        }
        quiz.translateQuote?.let {
            shownTranslateQuote()
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

@Composable
fun CharacterImage(url: String) {
    AndroidView(
        factory = ::WebView,
        update = { webView ->
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url)
        },
        modifier = Modifier
            .height(320.dp)
            .fillMaxWidth()
    )
}

@Composable
@Preview
fun QuizItemPreview() {
    QuizItem(
        Quiz(
            id = 1,
            character = "Luffy",
            quote = "I'm going to become the king of the pirates!",
            translateQuote = "海賊王に俺はなる！",
            characterUrl = "https://www.google.com/search?tbm=isch&q=One Piece+Luffy"
        ),
        onQuoteTranslate = {}
    )
}