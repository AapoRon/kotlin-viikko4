
package com.example.viikko1
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.viikko1.domain.*
import java.time.LocalDate



class TaskViewModel : ViewModel() {

    private val allTasks = mutableStateOf(listOf<Task>())

    var tasks = mutableStateOf(listOf<Task>())
        private set

    init {
        allTasks.value = mockTasks
        tasks.value = mockTasks
    }

    fun addTask(title: String) {
        val newTask = Task(
            id = (allTasks.value.maxOfOrNull { it.id } ?: 0) + 1,
            title = title,
            description = "",
            priority = 1,
            dueDate = LocalDate.now().plusDays(1),
            done = false
        )

        allTasks.value = allTasks.value + newTask
        tasks.value = allTasks.value
    }

    fun toggleDone(id: Int) {
        allTasks.value = allTasks.value.map {
            if (it.id == id) it.copy(done = !it.done) else it
        }
        tasks.value = allTasks.value
    }

    fun removeTask(id: Int) {
        allTasks.value = allTasks.value.filterNot { it.id == id }
        tasks.value = allTasks.value
    }

    // 🔍 FILTER
    fun filterByDone(done: Boolean) {
        tasks.value = allTasks.value.filter { it.done == done }
    }

    // 📅 SORT
    fun sortByDueDate() {
        tasks.value = tasks.value.sortedBy { it.dueDate }
    }

    // 🔄 Palauta kaikki
    fun showAll() {
        tasks.value = allTasks.value
    }
}
