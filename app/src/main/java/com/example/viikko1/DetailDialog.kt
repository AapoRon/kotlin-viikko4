package com.example.viikko1

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.viikko1.domain.Task

@Composable
fun DetailDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    onDelete: () -> Unit
) {

    var title by remember { mutableStateOf(task.title) }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text("Muokkaa tehtävää")
        },

        text = {
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth()
            )
        },

        confirmButton = {
            TextButton(
                onClick = { onSave(title) }
            ) {
                Text("Tallenna")
            }
        },

        dismissButton = {
            TextButton(
                onClick = onDelete
            ) {
                Text("Poista")
            }
        }
    )
}
