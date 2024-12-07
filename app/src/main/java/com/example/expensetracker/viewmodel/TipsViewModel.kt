package com.example.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.ApiClient
import com.example.expensetracker.data.datasource.model.model.Tip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TipsViewModel : ViewModel() {

    private val _tips = MutableStateFlow<List<Tip>>(emptyList())
    val tips: StateFlow<List<Tip>> get() = _tips

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchTips() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val tips = ApiClient.apiService.getTips()
                _tips.value = tips
            } catch (e: Exception) {
                _error.value = "Failed to load tips: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
