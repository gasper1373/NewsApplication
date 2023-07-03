package com.example.newsapplication

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Composable
fun WebViewScreen(url: String) {
    val context = LocalContext.current
    val webViewClient = remember {
        object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                // Show loading indicator or perform any necessary actions
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                // Perform any necessary actions when the page finishes loading
            }
        }
    }
    /*val fab = FloatingActionButton(

        onClick = { /* do something */ },
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp),

        ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }

     */
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    // Configure WebView settings if needed
                    this.webViewClient = webViewClient
                    loadUrl(url)
                }
            },
            update = { view ->
                view.loadUrl(url)
            }
        )
    }
