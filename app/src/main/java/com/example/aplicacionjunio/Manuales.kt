package com.example.aplicacionjunio

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class Manuales : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manuales)

        val webView: WebView = findViewById(R.id.webview)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        val videoId = "IVAJrYOOtwI"
        val videoHtml = """
            <html>
                <body style="margin:0;padding:0;background-color:#f0f0f0;">
                    <iframe width="100%" height="100%" src="https://www.youtube.com/embed/$videoId" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL(null, videoHtml, "text/html", "UTF-8", null)
    }
}
