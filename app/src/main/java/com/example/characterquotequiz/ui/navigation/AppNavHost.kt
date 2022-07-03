package com.example.characterquotequiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.characterquotequiz.ui.quiz.QuizListScreen
import com.example.characterquotequiz.ui.quiz.QuizViewModel
import com.example.characterquotequiz.ui.webview.CharacterImage

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
