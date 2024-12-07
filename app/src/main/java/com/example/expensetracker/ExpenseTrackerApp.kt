package com.example.expensetracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// La anotación @HiltAndroidApp inicializa Hilt en toda la aplicación
@HiltAndroidApp
class ExpenseTrackerApp : Application() {

    // El método onCreate() no necesita ser modificado, Hilt se encarga de la inicialización
    override fun onCreate() {
        super.onCreate()
        // Aquí puedes poner cualquier código adicional de inicialización si lo necesitas
    }
}