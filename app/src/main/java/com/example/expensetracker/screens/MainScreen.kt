package com.example.expensetracker.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.viewmodel.ExpenseViewModel
import com.example.expensetracker.viewmodel.FakeExpenseViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.Locale

@Composable
fun Context.getLocalizedContext(languageCode: String): Context {
    if (LocalInspectionMode.current) {
        // Si estamos en modo de vista previa, retornar el contexto actual
        return this
    }

    val config = resources.configuration
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    return createConfigurationContext(config) ?: this
}

@Composable
fun MainScreen(
    navController: NavController, expenseViewModel: ExpenseViewModel = viewModel()
) {
    val currentLanguage = expenseViewModel.currentLanguage.observeAsState("en") // Idioma actual
    val context = LocalContext.current

    // Contexto localizado según el idioma seleccionado
    val localizedContext = context.getLocalizedContext(currentLanguage.value)

    val expenses = expenseViewModel.expenses.observeAsState(emptyList()).value
    val totalExpenses = if (expenses.isNotEmpty()) expenses.sumOf { it.amount } else 0.0

    val monthlyIncomeInput = expenseViewModel.monthlyIncomeInput.observeAsState("0")
    val monthlyIncome = monthlyIncomeInput.value.toDoubleOrNull() ?: 0.0
    val profit = monthlyIncome - totalExpenses

    val chartData = remember { mutableStateOf(listOf<PieEntry>()) }

    LaunchedEffect(monthlyIncome, totalExpenses, currentLanguage.value) {
        chartData.value = listOf(
            PieEntry(totalExpenses.toFloat(),localizedContext.resources.getString(R.string.expense)),
            PieEntry(profit.toFloat(),localizedContext.resources.getString(R.string.profit)) // Localiza "Profit"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = localizedContext.resources.getString(R.string.expenses_vs_income), style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = monthlyIncomeInput.value,
            onValueChange = { input ->
                if (input.matches(Regex("^\\d*\\.?\\d*\$"))) {
                    expenseViewModel.monthlyIncomeInput.value = input
                }
            },
            label = { Text(text = localizedContext.resources.getString(R.string.enter_monthly_income)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (totalExpenses > monthlyIncome) {
            Text(
                text = localizedContext.resources.getString(R.string.warning_expenses_exceed),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (expenses.isNotEmpty()) {
            PieChartView(data = chartData.value, isNegativeProfit = profit < 0, languageCode = currentLanguage.value)
        } else {
            Text("Loading data...", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row (

        ) {
            Column {
                Button(onClick = { navController.navigate("add_expense") }) {
                    Text(text = localizedContext.resources.getString(R.string.add_expense))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.navigate("expense_list") }) {
                    Text(text = localizedContext.resources.getString(R.string.view_expenses))
                }
            }
            Column {
                
                Button(onClick = { navController.navigate("tips") }) {
                    Text(text = localizedContext.resources.getString(R.string.money_tips))
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Botón para cambiar de idioma
                Button(onClick = {
                    expenseViewModel.currentLanguage.value = if (currentLanguage.value == "en") "es" else "en"
                    Log.d("Localization", "Button clicked, currentLanguage set to: ${currentLanguage.value}") // Log al presionar botón
                }) {
                    Text(
                        text = if (currentLanguage.value == "en") "Cambiar a Español" else "Switch to English"
                    )
                }
            }

        }


        Spacer(modifier = Modifier.height(16.dp))


    }
}


@Composable
fun PieChartView(data: List<PieEntry>, isNegativeProfit: Boolean, languageCode: String) {
    val context = LocalContext.current
    val localizedContext = context.getLocalizedContext(languageCode)

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        factory = { ctx ->
            PieChart(ctx).apply {
                this.setDrawHoleEnabled(true)
                this.holeRadius = 40f
                this.setCenterTextSize(18f)
            }
        },
        update = { pieChart ->
            val colors = if (isNegativeProfit) {
                listOf(android.graphics.Color.RED) // Todo rojo
            } else {
                ColorTemplate.MATERIAL_COLORS.toList() // Colores normales
            }

            val dataSet = PieDataSet(data, localizedContext.resources.getString(R.string.summary_label)).apply {
                this.colors = colors
                valueTextColor = android.graphics.Color.WHITE
                valueTextSize = 16f
            }

            // Actualizamos el texto de descripción dinámicamente
            pieChart.description = Description().apply {
                text = localizedContext.resources.getString(R.string.expenses_vs_income)
                textSize = 12f
            }

            pieChart.centerText = localizedContext.resources.getString(R.string.budget)
            pieChart.data = PieData(dataSet)
            pieChart.invalidate() // Forzar redibujado
        }
    )
}


@Preview(showBackground = true, apiLevel = 33)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    val fakeExpenseViewModel = FakeExpenseViewModel() // Usamos el Fake ViewModel

    MainScreen(navController = navController, expenseViewModel = fakeExpenseViewModel)
}
