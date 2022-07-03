package com.example.characterquotequiz.ui

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


@Composable
fun TopBar(title: Int) {
    TopAppBar(
        title = { Text(stringResource(title)) }
    )
}