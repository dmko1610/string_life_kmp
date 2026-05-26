package dmitrykovalev.stringlife.viewmodel

import dmitrykovalev.stringlife.models.InstrumentType
import dmitrykovalev.stringlife.repository.InstrumentEntity
import dmitrykovalev.stringlife.repository.InstrumentRepository
import dmitrykovalev.stringlife.sync.SyncManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed class UiState {
    object Loading : UiState()
    data class Success(val instruments: List<InstrumentEntity>, val syncError: String? = null) :
        UiState()

    data class Error(val message: String) : UiState()
}

class InstrumentViewModel(
    private val repository: InstrumentRepository, private val syncManager: SyncManager
) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadInstruments()
        scope.launch { runSync() }
    }

    private fun loadInstruments() {
        scope.launch {
            try {
                repository.watchAll().collect { instruments ->
                    val current = _uiState.value
                    val syncError = (current as? UiState.Success)?.syncError
                    _uiState.value = UiState.Success(instruments, syncError)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addInstrument(name: String, type: InstrumentType, lastStringChangeDate: LocalDate?) {
        scope.launch {
            repository.addInstrument(name, type, lastStringChangeDate)
            runSync()
        }
    }

    fun deleteInstrument(id: String) {
        scope.launch {
            repository.deleteInstrument(id)
            runSync()
        }
    }

    fun onCleared() {
        scope.cancel()
    }

    fun clearSyncError() {
        val current = _uiState.value
        if (current is UiState.Success) {
            _uiState.value = current.copy(syncError = null)
        }
    }

    private suspend fun runSync() {
        try {
            syncManager.sync()
        } catch (e: Exception) {
            val current = _uiState.value
            if (current is UiState.Success) {
                _uiState.value = current.copy(syncError = "Sync failed. Changes saved locally")
            }
        }
    }

}