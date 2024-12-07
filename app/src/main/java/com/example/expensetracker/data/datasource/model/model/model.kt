package com.example.expensetracker.data.datasource.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val amount: Double,
    val date: Long // Guardamos la fecha como timestamp (milisegundos)
)
