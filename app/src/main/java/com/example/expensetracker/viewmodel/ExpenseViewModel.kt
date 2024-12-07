package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.datasource.model.datasource.ExpenseDao
import com.example.expensetracker.data.datasource.model.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class ExpenseViewModel @Inject constructor(
    private val expenseDao: ExpenseDao,
    application: Application
) : AndroidViewModel(application) {

    open val currentLanguage = MutableLiveData("en")
    open val monthlyIncomeInput = MutableLiveData("0") // Valor inicial
    open val expenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao.insertExpense(expense)
        }
    }

    fun deleteExpense(expenseId: Long) {
        viewModelScope.launch {
            expenseDao.deleteExpense(expenseId)
        }
    }
}


