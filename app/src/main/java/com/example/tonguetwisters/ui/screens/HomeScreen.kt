@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tonguetwisters.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tonguetwisters.R
import com.example.tonguetwisters.database.SortType
import com.example.tonguetwisters.database.TongueTwister
import com.example.tonguetwisters.database.TongueTwistersEvent
import com.example.tonguetwisters.database.TongueTwistersState
import com.example.tonguetwisters.database.toLocalizedString
import com.example.tonguetwisters.ui.screens.destinations.SettingsScreenDestination
import com.example.tonguetwisters.ui.screens.destinations.TongueTwisterContentScreenDestination
import com.example.tonguetwisters.utils.shareText
import com.example.tonguetwisters.ui.theme.md_theme_light_onSurface
import com.example.tonguetwisters.ui.theme.md_theme_light_surface
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    state: TongueTwistersState,
    onEvent: (TongueTwistersEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Скороговорки", maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(SettingsScreenDestination())
                    }) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
                    }
                },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                Chips(
                    state = state,
                    onEvent = onEvent
                )
                CardsContent(
                    state = state,
                    navigator = navigator,
                    onEvent = onEvent
                )
            }
        }
    )
}

@Composable
fun Chips(state: TongueTwistersState, onEvent: (TongueTwistersEvent) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        SortType.values().forEach { sortType ->
            item {
                FilterChip(
                    selected = state.sortType == sortType,
                    onClick = { onEvent(TongueTwistersEvent.SortTongueTwisters(sortType)) },
                    label = { Text(text = sortType.toLocalizedString()) },
                    leadingIcon = {
                        val isIconVisible = state.sortType == sortType
                        AnimatedVisibility(
                            visible = isIconVisible,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Localized Description",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    }
                )
            }
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter") // Because we don't use SortType in Animated Content
@Composable
fun CardsContent(
    state: TongueTwistersState,
    navigator: DestinationsNavigator,
    onEvent: (TongueTwistersEvent) -> Unit
) {
    AnimatedVisibility(
        visible = state.tongueTwisters.isEmpty(),
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Text(
            text = "Кажется тут ничего нет",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
    AnimatedContent(
        targetState = state.sortType,
        transitionSpec = {
            fadeIn() + scaleIn() togetherWith fadeOut() + scaleOut(
                animationSpec = tween(100)
            )
        }
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            items(state.tongueTwisters) { tongueTwister ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium),
                    onClick = {
                        navigator.navigate(TongueTwisterContentScreenDestination(id = tongueTwister.id))
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = tongueTwister.image_url,
                            contentDescription = "Preview",
                            contentScale = ContentScale.Fit
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            md_theme_light_onSurface.copy(alpha = .6f)
                                        ),
                                        endY = 90f
                                    )
                                )
                        ) {
                            Text(
                                text = tongueTwister.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = md_theme_light_surface,
                                modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp)
                            )
                            ActionButtons(
                                onEvent = onEvent,
                                tongueTwister = tongueTwister,
                                tint = md_theme_light_surface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionButtons(
    onEvent: (TongueTwistersEvent) -> Unit,
    tongueTwister: TongueTwister,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    var favorite by remember {
        mutableStateOf(tongueTwister.favorite)
    }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End)
    ) {
        IconButton(onClick = {
            val shareText = buildAnnotatedString {
                tongueTwister.content.split("\\\\").forEach { part ->
                    append(part)
                }
            }
            context.shareText(shareText)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_share_fill1),
                contentDescription = "Share",
                tint = tint
            )
        }
        FilledIconButton(onClick = {
            favorite = !favorite
            onEvent(TongueTwistersEvent.UpdateTongueTwister(tongueTwister = tongueTwister.copy(
                favorite = favorite
            )))
        }) {
            AnimatedContent(
                targetState = tongueTwister.favorite,
                transitionSpec = {
                    scaleIn() togetherWith scaleOut()
                }
            ) { isFavorite ->
                val iconResId = if (isFavorite) R.drawable.icon_book_fill1 else R.drawable.icon_book_fill0
                val contentDescription = if (isFavorite) "Undo favorite" else "Favorite"

                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = contentDescription,
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }
}