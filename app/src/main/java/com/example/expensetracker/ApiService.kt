package com.example.expensetracker

import com.example.expensetracker.data.datasource.model.model.Tip
import retrofit2.http.GET

interface ApiService {
    @GET("api/tips") // Reemplaza con el endpoint correcto
    suspend fun getTips(): List<Tip>
}