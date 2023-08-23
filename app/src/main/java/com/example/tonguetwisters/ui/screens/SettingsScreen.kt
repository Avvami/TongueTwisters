package com.example.tonguetwisters.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tonguetwisters.R
import com.example.tonguetwisters.database.TongueTwistersEvent
import com.example.tonguetwisters.database.TongueTwistersState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    state: TongueTwistersState,
    onEvent: (TongueTwistersEvent) -> Unit
) {
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
                    Text(text = "Настройки", maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                // Headline part
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Скороговорки",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
                // Big rounded image part
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .height(230.dp)
                                .width(260.dp)
                                .clip(RoundedCornerShape(62.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_bg),
                                contentDescription = "Logo Background",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = Modifier.fillMaxSize(.7f).align(Alignment.Center),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
                // Information cards part
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .height(IntrinsicSize.Max)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InformationCard(
                            painter = painterResource(id = R.drawable.icon_cognition_fill1),
                            contentDescription = "Phonetic puzzle",
                            titleText = "Фонетические головоломки",
                            bodyText = "Испытайте свою речь и ослепите всех"
                        )
                        InformationCard(
                            painter = painterResource(id = R.drawable.icon_graphic_eq_fill1),
                            contentDescription = "Tongue twister talent",
                            titleText = "Талант скороговорок",
                            bodyText = "Настоящее испытание для голосовых связок"
                        )
                        InformationCard(
                            painter = painterResource(id = R.drawable.icon_exercise_fill1),
                            contentDescription = "Challenging task",
                            titleText = "Сложный вызов",
                            bodyText = "Для тех, кто осмелится принять"
                        )
                        InformationCard(
                            painter = painterResource(id = R.drawable.icon_extension_fill1),
                            contentDescription = "Wordplay",
                            titleText = "Игры слов",
                            bodyText = "Настолько сложно, что аж язык заплетается"
                        )
                        InformationCard(
                            painter = painterResource(id = R.drawable.icon_match_case_fill1),
                            contentDescription = "Long phrases",
                            titleText = "Длинные фразы",
                            bodyText = "Зуб даю, сложно будет сказать"
                        )
                    }
                }
                // Theme change part
                item {
                    val icon: (@Composable () -> Unit) = if (state.isDarkMode) {
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_dark_mode_fill1),
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else {
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_light_mode_fill1),
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = .05f))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Тёмная тема",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(20.dp)
                            )
                            Switch(
                                modifier = Modifier
                                    .semantics { contentDescription = "Dark mode" }
                                    .padding(end = 20.dp),
                                checked = state.isDarkMode,
                                onCheckedChange = { onEvent(TongueTwistersEvent.ToggleDarkMode(it)) },
                                thumbContent = icon
                            )
                        }
                    }
                }
                // About app part
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(color = MaterialTheme.colorScheme.primary.copy(alpha = .05f))
                    ) {
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "О приложении",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Это интерактивное и увлекательное приложение, созданное специально для тех, кто хочет улучшить свою дикцию, артикуляцию и речь. При помощи разнообразных скороговорок, пользователи могут развивать навыки языка и укреплять свои произносительные способности.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    )
}

@Composable
fun InformationCard(
    painter: Painter,
    contentDescription: String,
    titleText: String,
    bodyText: String
) {
    Box(
        modifier = Modifier
            .width(138.dp)
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                text = titleText,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )
            Text(
                text = bodyText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}