package ru.eltech.oop.budgeapp

import java.time.LocalDate

data class Transaction(
        val amount: Double,
        val date: LocalDate,
        val category: Category,
        val currency: Currency,
        val comment: String = ""
)