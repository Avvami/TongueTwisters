package com.example.tonguetwisters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.tonguetwisters.database.TongueTwistersDatabase
import com.example.tonguetwisters.database.TongueTwistersViewModel
import com.example.tonguetwisters.prefdatastore.PreferencesDatastore
import com.example.tonguetwisters.ui.screens.HomeScreen
import com.example.tonguetwisters.ui.screens.NavGraphs
import com.example.tonguetwisters.ui.screens.SettingsScreen
import com.example.tonguetwisters.ui.screens.TongueTwisterContentScreen
import com.example.tonguetwisters.ui.screens.destinations.HomeScreenDestination
import com.example.tonguetwisters.ui.screens.destinations.SettingsScreenDestination
import com.example.tonguetwisters.ui.screens.destinations.TongueTwisterContentScreenDestination
import com.example.tonguetwisters.ui.theme.TongueTwistersTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable

class MainActivity : ComponentActivity() {

    private lateinit var dataStorePreferences: PreferencesDatastore

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            TongueTwistersDatabase::class.java,
            "tongue_twisters_database"
        ).createFromAsset("database/tongue_twisters.db").build()
    }

    @Suppress("UNCHECKED_CAST")
    private val viewModel by viewModels<TongueTwistersViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TongueTwistersViewModel(database.tongueTwistersDao(), dataStorePreferences) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        dataStorePreferences = PreferencesDatastore(this)

        setContent {
            val state by viewModel.state.collectAsState()
            TongueTwistersTheme(darkTheme = state.isDarkMode) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                        .safeDrawingPadding()
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root) {
                        composable(HomeScreenDestination) {
                            HomeScreen(
                                navigator = destinationsNavigator,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable(SettingsScreenDestination) {
                            SettingsScreen(
                                navigator = destinationsNavigator,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable(TongueTwisterContentScreenDestination) {
                            TongueTwisterContentScreen(
                                navigator = destinationsNavigator,
                                state = state,
                                onEvent = viewModel::onEvent,
                                id = navArgs.id
                            )
                        }
                    }
                }
            }
        }
    }
}