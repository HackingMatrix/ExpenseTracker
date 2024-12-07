package com.example.expensetracker.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensetracker.viewmodel.TipsViewModel

@Composable
fun TipsScreen(navController: NavController, tipsViewModel: TipsViewModel = viewModel()) {
    val tips by tipsViewModel.tips.collectAsState()
    val isLoading by tipsViewModel.isLoading.collectAsState()
    val error by tipsViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        tipsViewModel.fetchTips()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Financial Tips", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Text("Loading tips...")
            }
            error != null -> {
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            }
            tips.isNotEmpty() -> {
                LazyColumn {
                    items(tips) { tip ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(tip.title, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(tip.description, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
            else -> {
                Text("No tips available")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        Button(
            onClick = { navController.navigate("main_screen") }, // Navigates back to the previous screen
            modifier = Modifier.align(Alignment.CenterHorizontally) // Center the button
        ) {
            Text("Back")
        }
    }
}
