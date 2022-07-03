package com.example.characterquotequiz.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.characterquotequiz.ui.quiz.QuizListScreen
import com.example.characterquotequiz.ui.quiz.QuizViewModel
import com.example.characterquotequiz.ui.theme.CharacterQuoteQuizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<QuizViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CharacterQuoteQuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val errorMessage by viewModel.errorMessage.observeAsState()
                    errorMessage?.let { showToast(it) }

                    QuizListScreen(viewModel, errorMessage == null)
                }
            }
        }
    }

    private fun showToast(messageResource: Int) {
        Toast.makeText(this, messageResource, Toast.LENGTH_SHORT).show()
    }
}