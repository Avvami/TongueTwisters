package com.example.tonguetwisters.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.tonguetwisters.database.TongueTwistersEvent
import com.example.tonguetwisters.database.TongueTwistersState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TongueTwisterContentScreen(
    navigator: DestinationsNavigator,
    state: TongueTwistersState,
    onEvent: (TongueTwistersEvent) -> Unit,
    id: Int
) {
    val tongueTwister = state.tongueTwisters.find { it.id == id }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    if (tongueTwister != null) {
                        Text(text = tongueTwister.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            )
        },
        content = { innerPadding ->
            if (tongueTwister == null) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {
                    Text(
                        text = "Произошла ошибка при отображении контента",
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            } else {
                val annotatedText = buildAnnotatedString {
                    tongueTwister.content.split("\\\\").forEachIndexed { index, part ->
                        if (index % 2 == 0) {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                append(part)
                            }
                        } else {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.error
                                )
                            ) {
                                append(part)
                            }
                        }
                    }
                }
                LazyColumn(
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp)
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                        ) {
                            ActionButtons(
                                onEvent = onEvent,
                                tongueTwister = tongueTwister
                            )
                            Text(
                                text = annotatedText,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 16.dp, end = 16.dp, bottom = 28.dp),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    )
}