package com.example.expensetracker.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.viewmodel.ExpenseViewModel
import com.example.expensetracker.viewmodel.FakeExpenseViewModel


@Composable
fun ExpenseListScreen(navController: NavController, expenseViewModel: ExpenseViewModel = viewModel()) {
    // Observa la lista de gastos del ViewModel
    val expenses by expenseViewModel.expenses.observeAsState(emptyList()) // Asegúrate de que 'expenses' es una lista de 'Expense'
    val currentLanguage = expenseViewModel.currentLanguage.observeAsState("en") // Idioma actual
    val context = LocalContext.current
    val localizedContext = context.getLocalizedContext(currentLanguage.value)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(localizedContext.resources.getString(R.string.expenses_list), style = MaterialTheme.typography.headlineMedium)

        // Botón para agregar un nuevo gasto
        Button(
            onClick = { navController.navigate("add_expense") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(localizedContext.resources.getString(R.string.add_new_expense))
        }
        Button(onClick = { navController.navigate("main_screen") }) {
            Text(text = localizedContext.resources.getString(R.string.back_button))
        }

        // Mostrar la lista de gastos
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(expenses) { expense ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Mostrar el nombre y la cantidad de cada gasto
                        Column {
                            Text(expense.name)
                            Text("$${expense.amount}")
                        }
                        // Botón para eliminar el gasto
                        Button(
                            onClick = { expenseViewModel.deleteExpense(expense.id) },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(localizedContext.resources.getString(R.string.delete_button))
                        }

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        Button(
            onClick = { navController.navigate("main_screen") }, // Navigates back to the previous screen
            modifier = Modifier.align(Alignment.CenterHorizontally) // Center the button
        ) {
            Text(text = localizedContext.resources.getString(R.string.back_button))
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun ExpenseListScreenPreview() {
    val navController = rememberNavController()
    val fakeExpenseViewModel = FakeExpenseViewModel()

    ExpenseListScreen(navController = navController, expenseViewModel = fakeExpenseViewModel)
}
