package com.example.viikko1.domain

import java.time.LocalDate

val mockTasks = listOf(
    Task(1, "Osta maitoa", "Käy kaupassa", 1, LocalDate.now().plusDays(1), false),
    Task(2, "Tee läksyt", "Ohjelmointi", 2, LocalDate.now().plusDays(2), false),
    Task(3, "Siivoa", "Imurointi", 3, LocalDate.now(), true),
    Task(4, "Liikunta", "Kävelylenkki", 2, LocalDate.now().plusDays(3), false),
    Task(5, "Lue kirjaa", "30 min", 1, LocalDate.now().plusDays(5), true)
)
