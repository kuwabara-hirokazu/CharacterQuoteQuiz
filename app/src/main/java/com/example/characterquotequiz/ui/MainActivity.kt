package com.example.characterquotequiz.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.characterquotequiz.ui.quiz.QuizListScreen
import com.example.characterquotequiz.ui.quiz.QuizViewModel
import com.example.characterquotequiz.ui.theme.CharacterQuoteQuizTheme
import com.example.characterquotequiz.ui.webview.CharacterImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<QuizViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getQuizList()
        setContent {
            CharacterQuoteQuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val errorMessage by viewModel.errorMessage.observeAsState()
                    errorMessage?.let { showToast(it) }

                    val navController = rememberNavController()

                    AppNavHost(viewModel, errorMessage, navController)
                }
            }
        }
    }

    private fun showToast(messageResource: Int) {
        Toast.makeText(this, messageResource, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun AppNavHost(viewModel: QuizViewModel, errorMessage: Int?, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.destination
    ) {
        composable(NavigationDestination.destination) {
            QuizListScreen(
                viewModel,
                errorMessage == null,
                navController
            )
        }
        composable(
            route = "${NavigationDestination.route}/{${NavigationDestination.urlArg}}",
            arguments = listOf(
                navArgument(NavigationDestination.urlArg) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val url = navBackStackEntry.arguments?.getString(NavigationDestination.urlArg) ?: ""
            CharacterImage(url)
        }
    }
}

object NavigationDestination {
    const val route = "character_image_route"
    const val destination = "quiz_route"
    const val urlArg = "url"
}