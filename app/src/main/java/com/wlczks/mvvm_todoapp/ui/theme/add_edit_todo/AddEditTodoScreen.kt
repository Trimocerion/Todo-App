package com.wlczks.mvvm_todoapp.ui.theme.add_edit_todo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wlczks.mvvm_todoapp.util.UiEvent
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )


                }

                else -> Unit
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            TextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Tytuł")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                },
                placeholder = {
                    Text(text = "Opis")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
            // TODO: zrobic czas wprowadzania

            val snackState = remember { SnackbarHostState() }
            val snackScope = rememberCoroutineScope()

            var dateValue by remember { mutableStateOf("") }
            var showDateView by remember { mutableStateOf(false) }
            var showTimeView by remember { mutableStateOf(false) }

            val datePickerState = rememberDatePickerState(
                initialDisplayMode = DisplayMode.Picker,
                initialSelectedDateMillis = null,
            )

            val confirmEnabled = datePickerState.selectedDateMillis != null

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { showDateView = true }) {
                Text(text = dateValue.ifEmpty { "Wybierz datę" })
            }

            if (showDateView) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDateView = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDateView = false
                                val selectedDate =
                                    Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0L)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                dateValue =
                                    selectedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                                snackScope.launch {
                                    snackState.showSnackbar(
                                        "Selected date: $dateValue"
                                    )
                                }
                            },
                            enabled = confirmEnabled
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDateView = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // SnackbarHost
            SnackbarHost(hostState = snackState, modifier = Modifier)

            // TODO:  Trzeba zrobic Timepicker


            // TODO: Flaga priorytetu

        /*
                       val priorityOptions = listOf("Low", "Medium", "High")

                       var priority by remember {
                           mutableStateOf(0)
                       }

                            Text(text = "Priority")

                           DropdownMenu(
                                expanded = false, // Toggle expanded state based on user interaction
                                onDismissRequest = { *//* Handle dismiss request *//* },
                modifier = Modifier.fillMaxWidth()
            ) {
                priorityOptions.forEachIndexed { index, option ->
                    DropdownMenuItem(text = { *//*TODO*//* }, onClick = { *//*TODO*//* })
                    Text(text = option)
                }
            }
            */


        }
    }
}