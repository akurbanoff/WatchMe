package app.sample.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sample.db.dao.SampleDao
import app.sample.states.SampleState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn


class SampleViewModel(private val dao: SampleDao): ViewModel() {

    private val _state = MutableStateFlow(SampleState())
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _samples = MutableStateFlow(dao.getAll()).flatMapLatest { dao.getAll() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _samples){state, samples ->
        state.copy(
            samples = samples
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SampleState())

}