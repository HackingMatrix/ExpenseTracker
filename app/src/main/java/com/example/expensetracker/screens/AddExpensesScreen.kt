package com.example.expensetracker.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.data.datasource.model.model.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel
import com.example.expensetracker.viewmodel.FakeExpenseViewModel



@Composable
fun AddExpenseScreen(navController: NavController, expenseViewModel: ExpenseViewModel = viewModel()) {
    val currentLanguage = expenseViewModel.currentLanguage.observeAsState("en") // Idioma actual
    val context = LocalContext.current

    // Contexto localizado según el idioma seleccionado
    val localizedContext = context.getLocalizedContext(currentLanguage.value)
    var expenseName by remember { mutableStateOf("") }
    var expenseAmount by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = localizedContext.resources.getString(R.string.add_expense), style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de nombre del gasto
        BasicTextField(
            value = expenseName,
            onValueChange = { expenseName = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp).border(1.dp, MaterialTheme.colorScheme.outline),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            decorationBox = { innerTextField ->
                Box(Modifier.padding(16.dp)) {
                    if (expenseName.isEmpty()) {
                        Text(text = localizedContext.resources.getString(R.string.expense_name_hint), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de cantidad
        BasicTextField(
            value = expenseAmount,
            onValueChange = { expenseAmount = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp).border(1.dp, MaterialTheme.colorScheme.outline),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            decorationBox = { innerTextField ->
                Box(Modifier.padding(16.dp)) {
                    if (expenseAmount.isEmpty()) {
                        Text(text = localizedContext.resources.getString(R.string.amount_hint), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    }
                    innerTextField()
                }
            }
        )

        if (isError) {
            Text(text = localizedContext.resources.getString(R.string.error_message), color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar el gasto
        Button(
            onClick = {
                if (expenseName.isNotEmpty() && expenseAmount.isNotEmpty()) {
                    val amount = expenseAmount.toDoubleOrNull()
                    if (amount != null) {
                        expenseViewModel.addExpense(
                            expense = Expense(
                                name = expenseName,
                                amount = amount,
                                date = System.currentTimeMillis() // Usamos la fecha actual
                            )
                        )
                        navController.popBackStack() // Regresar a la pantalla anterior
                    } else {
                        isError = true
                    }
                } else {
                    isError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = localizedContext.resources.getString(R.string.add_expense_button))
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
fun AddExpenseScreenPreview() {
    // Crear un NavController simulado
    val navController = rememberNavController()

    // Usar el FakeExpenseViewModel simulado
    val fakeExpenseViewModel = FakeExpenseViewModel()

    // Llamar a la pantalla de agregar gasto con el navController y el ViewModel simulado
    AddExpenseScreen(navController = navController, expenseViewModel = fakeExpenseViewModel)
}


