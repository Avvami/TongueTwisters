package com.example.tonguetwisters.utils

import android.content.Context
import android.content.Intent
import androidx.compose.ui.text.AnnotatedString

fun Context.shareText(text: AnnotatedString) {
    val sendIntent = Intent (
        Intent.ACTION_SEND
    ).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(
        sendIntent, null
    )
    startActivity(shareIntent)
}