package com.wlczks.mvvm_todoapp.ui.theme.add_edit_todo

sealed class AddEditTodoEvent {
    data class OnTitleChange(val title: String) : AddEditTodoEvent()
    data class OnDescriptionChange(val description: String) : AddEditTodoEvent()
    data class OnPriorityChange(val flagged: String) : AddEditTodoEvent()
    data class OnDateChange(val date: String) : AddEditTodoEvent()
    object OnSaveTodoClick : AddEditTodoEvent()
}
