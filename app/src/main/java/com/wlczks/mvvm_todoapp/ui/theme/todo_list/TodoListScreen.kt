package com.wlczks.mvvm_todoapp.ui.theme.todo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wlczks.mvvm_todoapp.data.Todo
import com.wlczks.mvvm_todoapp.notifications.TodoNotificationService
import com.wlczks.mvvm_todoapp.ui.theme.add_edit_todo.Priority
import com.wlczks.mvvm_todoapp.util.UiEvent


@Composable
fun ToDoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoListViewmodel = hiltViewModel(),
) {
    var sortingOption by remember {
        mutableStateOf(SortingOption.DATE)
    }
    var isSortDescending by remember {
        mutableStateOf(true)
    }


    val todos = viewModel.todos.collectAsState(initial = emptyList())

    val snackbarHostState = remember { SnackbarHostState() }


    val sortedTodos = remember(todos.value, sortingOption, isSortDescending) {
        when (sortingOption) {
            SortingOption.DATE -> todos.value.sortedBy { it.date }
                .let { if (isSortDescending) it else it.reversed() }

            SortingOption.TITLE -> todos.value.sortedBy { it.title.lowercase() }
                .let { if (isSortDescending) it else it.reversed() }

            SortingOption.PRIORITY -> todos.value.sortedWith(compareByDescending<Todo> { it.priority })
                .sortedByDescending { it.priority == Priority.HIGH.toString() }
                .let { if (isSortDescending) it else it.reversed() }
        }
    }


    //test
    val service = TodoNotificationService(LocalContext.current)
    //test

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }

                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TodoListEvent.OnAddTodoClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            // sorting buttons
            Row(Modifier.padding(horizontal = 16.dp)) {
                SortingButton(
                    text = "Date",
                    isSelected = sortingOption == SortingOption.DATE,
                    isSortDescending = isSortDescending,
                    onClick = {
                        sortingOption = SortingOption.DATE
                        isSortDescending = !isSortDescending
                    }
                )
                SortingButton(
                    text = "Priority",
                    isSelected = sortingOption == SortingOption.PRIORITY,
                    isSortDescending = isSortDescending,
                    onClick = {
                        sortingOption = SortingOption.PRIORITY
                        isSortDescending = !isSortDescending
                    }
                )
                SortingButton(
                    text = "Title",
                    isSelected = sortingOption == SortingOption.TITLE,
                    isSortDescending = isSortDescending,
                    onClick = {
                        sortingOption = SortingOption.TITLE
                        isSortDescending = !isSortDescending
                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = innerPadding
        ) {

            items(sortedTodos) { todo ->
                TodoItem(
                    todo = todo,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(TodoListEvent.OnTodoClick(todo))
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun SortingButton(
    text: String,
    isSelected: Boolean,
    isSortDescending: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text)
            if (isSelected) {
                Icon(
                    imageVector = if (isSortDescending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Sort Arrow"
                )
            }
        }
    }
}


enum class SortingOption {
    DATE,
    PRIORITY,
    TITLE,
}