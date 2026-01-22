package com.example.viikko1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.domain.*
import java.time.LocalDate

@Composable
fun HomeScreen() {

    var tasks by remember { mutableStateOf(mockTasks) }
    var newTaskTitle by remember { mutableStateOf("") }
    var filterMode by remember { mutableStateOf("ALL") }

    val visibleTasks = when (filterMode) {
        "DONE" -> filterByDone(tasks, true)
        "UNDONE" -> filterByDone(tasks, false)
        "SORT" -> sortByDueDate(tasks)
        else -> tasks
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Tehtävälista",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔘 FILTER & SORT NAPIT
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { filterMode = "SORT" }) {
                Text("Sort by date")
            }

            Button(onClick = { filterMode = "DONE" }) {
                Text("Show done")
            }

            Button(onClick = { filterMode = "UNDONE" }) {
                Text("Show undone")
            }

            Button(onClick = { filterMode = "ALL" }) {
                Text("Reset")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ➕ ADD TASK
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
                    val newTask = Task(
                        id = tasks.size + 1,
                        title = newTaskTitle,
                        description = "",
                        priority = 1,
                        dueDate = LocalDate.now().plusDays(1),
                        done = false
                    )
                    tasks = addTask(tasks, newTask)
                    newTaskTitle = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📋 TASK LISTA
        visibleTasks.forEach { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(task.title)

                Button(onClick = {
                    tasks = toggleDone(tasks, task.id)
                }) {
                    Text(if (task.done) "Undo" else "Done")
                }
            }
        }
    }
}
