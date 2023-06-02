package com.wlczks.mvvm_todoapp.ui.theme.todo_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wlczks.mvvm_todoapp.data.Todo

@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent) -> Unit,
    modifier: Modifier = Modifier
){

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ){

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,

                ) {
                Text(
                    text = todo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Checkbox(checked = todo.isDone, onCheckedChange = { isChecked ->
                    onEvent(TodoListEvent.OnDoneChange(todo, isChecked))
                })
                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                        .weight(1f)
                )
                IconButton(onClick = {
                    onEvent(TodoListEvent.OnDeleteTodoClick(todo))
                }) { Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete") }

            }

            todo.priority?.let { flagged ->
                Spacer(modifier = Modifier.height(8.dp))
                val flagColor = when (flagged) {
                    "High" -> Color.Red
                    "Normal" -> Color.Blue
                    "Low" -> Color.Green
                    else -> Color.Unspecified
                }
                Text(
                    text = flagged,
                    color = flagColor
                )
            }

            todo.date.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it.toString().format("dd.MM.yyyy"))
            }


            todo.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it)
            }
        }
    }
}
/*
@Preview
@Composable
fun TodoItemPreview() {
    TodoItem(
        todo = Todo(
            title = "Tytuł",
            description = "Opis",
            isDone = false,
        ),
        onEvent = { */
/* Define a preview onEvent action *//*
 }
    )
}
*/
