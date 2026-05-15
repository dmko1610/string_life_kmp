package dmitrykovalev.stringlife

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dmitrykovalev.stringlife.repository.InstrumentEntity
import dmitrykovalev.stringlife.viewmodel.InstrumentViewModel
import dmitrykovalev.stringlife.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: InstrumentViewModel, onAddInstrumentClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = onAddInstrumentClick) {
            Icon(Icons.Default.Add, contentDescription = "Add Instrument")
        }
    }) { innerPadding ->
        when (val state = uiState) {
            is UiState.Loading -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is UiState.Error -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(state.message)
            }

            is UiState.Success -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(state.instruments, key = { it.id }) { instrument ->
                    InstrumentItem(
                        instrument = instrument,
                        onDelete = { viewModel.deleteInstrument(instrument.id) })
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) { }
    }
}

@Composable
fun InstrumentItem(instrument: InstrumentEntity, onDelete: () -> Unit) {
    ListItem(headlineContent = { Text(instrument.name) }, supportingContent = {
        Text(instrument.lastStringChangeDate?.toString() ?: "No string change recorded")
    }, overlineContent = { Text(instrument.type.displayName) }, trailingContent = {
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    })
    HorizontalDivider()
}