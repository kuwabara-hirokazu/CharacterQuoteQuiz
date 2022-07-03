package com.example.characterquotequiz.ui.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.characterquotequiz.R
import com.example.characterquotequiz.ui.TopBar

@Composable
fun CharacterImage(url: String) {
    Column {
        TopBar(title = R.string.title_character_image)
        AndroidView(
            factory = ::WebView,
            update = { webView ->
                webView.webViewClient = WebViewClient()
                webView.loadUrl(url)
            },
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}