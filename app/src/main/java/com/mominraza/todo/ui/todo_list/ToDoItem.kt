package com.mominraza.todo.ui.todo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mominraza.todo.data.ToDo

@Composable
fun ToDoItem(
    toDo: ToDo,
    onEvent: (ToDoListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier.clickable {
        onEvent(ToDoListEvent.OnToDoClick(toDo))
    }) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        toDo.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton({
                        onEvent(ToDoListEvent.OnDeleteToDoClick(toDo))
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                toDo.description?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(it)
                }
            }
            Checkbox(
                toDo.isDone,
                {
                    onEvent(ToDoListEvent.OnDoneChange(toDo,it))
                }
            )
        }
    }
}
