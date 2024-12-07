package com.example.expensetracker


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetracker.screens.AddExpenseScreen
import com.example.expensetracker.screens.ExpenseListScreen

import com.example.expensetracker.screens.MainScreen
import com.example.expensetracker.screens.TipsScreen
import com.example.expensetracker.viewmodel.ExpenseViewModel

@Composable
fun AppNavigation(navController: NavHostController, expenseViewModel: ExpenseViewModel) {
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(navController = navController, expenseViewModel = expenseViewModel)
        }
        composable("add_expense") {
            AddExpenseScreen(navController = navController, expenseViewModel = expenseViewModel)
        }
        composable("expense_list") {
            ExpenseListScreen(navController = navController, expenseViewModel = expenseViewModel)
        }
        composable("tips") {
            TipsScreen(navController = navController)
        }
    }
}

