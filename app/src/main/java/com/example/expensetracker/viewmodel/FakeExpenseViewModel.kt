package com.example.expensetracker.viewmodel

import FakeExpenseDao
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.expensetracker.data.datasource.model.model.Expense


// Simulación del ViewModel
class FakeExpenseViewModel : ExpenseViewModel(
    expenseDao = FakeExpenseDao(),
    application = Application()
) {
    fun getAllExpenses(): List<Expense> {
        // Retorna datos de ejemplo
        return listOf(
            Expense(name = "Sample Expense 1", amount = 50.0, date = System.currentTimeMillis()),
            Expense(name = "Sample Expense 2", amount = 30.0, date = System.currentTimeMillis())
        )
    }

    fun addExpense2(expense: Expense) {
        // Simula la adición de un gasto, pero no realiza ninguna operación real
        println("Expense added: $expense")
    }

    private val _expenses = MutableLiveData(
        listOf(
            Expense(name = "Fake Expense 1", amount = 100.0, date = System.currentTimeMillis()),
            Expense(name = "Fake Expense 2", amount = 50.0, date = System.currentTimeMillis())
        )
    )
    override val expenses: MutableLiveData<List<Expense>> = _expenses

    // Simulamos el idioma con LiveData


    // Simulamos el ingreso mensual con LiveData
    private val _monthlyIncomeInput = MutableLiveData("1000.0")
    override val monthlyIncomeInput: MutableLiveData<String> = _monthlyIncomeInput
}
