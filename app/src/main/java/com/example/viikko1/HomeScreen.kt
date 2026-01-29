package com.example.viikko1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko1.domain.Task

@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel = viewModel()
) {

    val tasks: List<Task> = taskViewModel.tasks.value

    var newTaskTitle by remember { mutableStateOf("") }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Tehtävälista",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(16.dp))

        // ➕ Lisää task
        TextField(
            value = newTaskTitle,
            onValueChange = { newTaskTitle = it },
            label = { Text("Uusi tehtävä") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (newTaskTitle.isNotBlank()) {
                    taskViewModel.addTask(newTaskTitle)
                    newTaskTitle = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lisää tehtävä")
        }

        Spacer(Modifier.height(16.dp))

        // 🔘 Filternapit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = { taskViewModel.showAll() }) {
                Text("Kaikki")
            }

            Button(onClick = { taskViewModel.filterByDone(false) }) {
                Text("Tekemättömät")
            }

            Button(onClick = { taskViewModel.filterByDone(true) }) {
                Text("Tehdyt")
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { taskViewModel.sortByDueDate() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Järjestä deadline mukaan")
        }

        Spacer(Modifier.height(16.dp))

        // 📋 Lista
        LazyColumn {

            items(tasks) { task ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = {
                                taskViewModel.toggleDone(task.id)
                            }
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(task.title)
                    }

                    Row {

                        TextButton(onClick = {
                            selectedTask = task
                        }) {
                            Text("Edit")
                        }

                        TextButton(onClick = {
                            taskViewModel.removeTask(task.id)
                        }) {
                            Text("Poista")
                        }
                    }
                }
            }
        }
    }

    // 🔵 Detail dialog
    selectedTask?.let { task ->

        DetailDialog(
            task = task,
            onDismiss = { selectedTask = null },
            onSave = { newTitle ->
                taskViewModel.removeTask(task.id)
                taskViewModel.addTask(newTitle)
                selectedTask = null
            },
            onDelete = {
                taskViewModel.removeTask(task.id)
                selectedTask = null
            }
        )
    }
}
