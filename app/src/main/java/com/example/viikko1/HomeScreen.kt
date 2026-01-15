package com.example.viikko1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.domain.mockTasks

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Tehtävälista")

        Spacer(modifier = Modifier.height(16.dp))

        mockTasks.forEach { task ->
            Text(text = "• ${task.title}")
        }
    }
}
