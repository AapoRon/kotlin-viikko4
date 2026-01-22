package com.example.viikko1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel = viewModel()
) {
    val tasks = taskViewModel.tasks.value
    var newTaskTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔤 OTSIKKO
        Text(
            text = "Tehtävälista",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ➕ UUSI TEHTÄVÄ
        TextField(
            value = newTaskTitle,
            onValueChange = { newTaskTitle = it },
            label = { Text("Uusi tehtävä") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        // 🔍 FILTTERI- & JÄRJESTYSPAINIKKEET
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

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { taskViewModel.sortByDueDate() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Järjestä deadline mukaan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📋 TEHTÄVÄLISTA
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

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(task.title)
                    }

                    IconButton(onClick = {
                        taskViewModel.removeTask(task.id)
                    }) {
                        Text("🗑️")
                    }
                }
            }
        }
    }
}
