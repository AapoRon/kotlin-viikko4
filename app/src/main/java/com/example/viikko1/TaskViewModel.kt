package com.example.viikko1

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.viikko1.domain.Task
import com.example.viikko1.domain.mockTasks
import java.time.LocalDate

class TaskViewModel : ViewModel() {

    // 🔹 kaikki taskit (source of truth)
    private val allTasks = mutableStateOf(mockTasks)

    // 🔹 UI-tila
    var tasks = mutableStateOf(listOf<Task>())
        private set

    private var currentFilter: Boolean? = null
    private var sortByDate = false

    init {
        applyFilters()
    }

    // ========================
    // CRUD
    // ========================

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
        applyFilters()
    }

    fun toggleDone(id: Int) {
        allTasks.value = allTasks.value.map {
            if (it.id == id) it.copy(done = !it.done) else it
        }
        applyFilters()
    }

    fun removeTask(id: Int) {
        allTasks.value = allTasks.value.filterNot { it.id == id }
        applyFilters()
    }

    // ========================
    // FILTER & SORT
    // ========================

    fun filterByDone(done: Boolean) {
        currentFilter = done
        applyFilters()
    }

    fun showAll() {
        currentFilter = null
        applyFilters()
    }

    fun sortByDueDate() {
        sortByDate = true
        applyFilters()
    }

    // ========================
    // MAGIC
    // ========================

    private fun applyFilters() {

        var result = allTasks.value

        // filter
        currentFilter?.let { done ->
            result = result.filter { it.done == done }
        }

        // sort
        if (sortByDate) {
            result = result.sortedBy { it.dueDate }
        }

        tasks.value = result
    }
}
