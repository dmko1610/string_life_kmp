package dmitrykovalev.stringlife

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmitrykovalev.stringlife.models.InstrumentType
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeSelector(selected: InstrumentType, onSelect: (InstrumentType) -> Unit) {
    val options = InstrumentType.values().toList()
    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                selected = selected == option,
                onClick = { onSelect(option) },
                label = { Text(option.displayName) },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                icon = {
                    if (selected == option) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInstrumentScreen(onBack: () -> Unit, onSave: (String, InstrumentType, LocalDate?) -> Unit) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(InstrumentType.ELECTRIC) }
    var showDate by remember { mutableStateOf(false) }
    var pickedDate: LocalDate? by remember { mutableStateOf(null) }

    if (showDate) {
        DatePickerDialog(onDismissRequest = { showDate = false }, confirmButton = {
            TextButton(onClick = {
                showDate = false
            }) { Text("OK") }
        }, dismissButton = {
            TextButton(onClick = { showDate = false }) { Text("Cancel") }
        }) {
            val state = rememberDatePickerState()
            DatePicker(state = state)
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Add Instrument") }, navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }, bottomBar = {
        Button(
            onClick = { onSave(name, type, pickedDate) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) { Text("Save") }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Instrument name") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Type")
            TypeSelector(selected = type, onSelect = { type = it })

            OutlinedButton(onClick = { showDate = true }) {
                Text(pickedDate?.toString() ?: "Select replacement date")
            }
        }
    }
}
