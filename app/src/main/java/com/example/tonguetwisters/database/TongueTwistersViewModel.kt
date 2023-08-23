package com.example.tonguetwisters.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tonguetwisters.prefdatastore.PreferencesDatastore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TongueTwistersViewModel(
    private val tongueTwisterDatabaseDao: TongueTwistersDatabaseDao,
    private val dataStore: PreferencesDatastore
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.ALL)
    private var _isDarkMode = dataStore.isDarkMode
    private val _tongueTwisters = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.ALL -> tongueTwisterDatabaseDao.getAllTongueTwisters()
                SortType.FAVORITE -> tongueTwisterDatabaseDao.getTongueTwistersOrderedByFavorite(
                    favoriteFilter = true
                )
                SortType.LONG -> tongueTwisterDatabaseDao.getTongueTwistersOrderedByGenre(SortType.LONG.toLocalizedString())
                SortType.SHORT -> tongueTwisterDatabaseDao.getTongueTwistersOrderedByGenre(SortType.SHORT.toLocalizedString())
                SortType.CHILDISH -> tongueTwisterDatabaseDao.getTongueTwistersOrderedByGenre(SortType.CHILDISH.toLocalizedString())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(TongueTwistersState())
    val state = combine(_state, _sortType, _tongueTwisters, _isDarkMode) { state, sortType, tongueTwisters, isDarkMode ->
        state.copy(
            sortType = sortType,
            tongueTwisters = tongueTwisters,
            isDarkMode = isDarkMode
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TongueTwistersState())

    fun onEvent(event: TongueTwistersEvent) {
        when (event) {
            is TongueTwistersEvent.UpdateTongueTwister -> {
                viewModelScope.launch {
                    tongueTwisterDatabaseDao.updateTongueTwister(event.tongueTwister)
                }
            }
            is TongueTwistersEvent.SortTongueTwisters -> {
                _sortType.value = event.sortType
            }
            is TongueTwistersEvent.ToggleDarkMode -> {
                viewModelScope.launch {
                    dataStore.setDarkMode(event.darkMode)
                }
            }
        }
    }
}