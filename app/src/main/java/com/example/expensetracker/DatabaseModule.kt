package com.example.expensetracker

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.datasource.model.AppDatabase
import com.example.expensetracker.data.datasource.model.datasource.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.app.Application

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // Método para proveer el contexto de la aplicación
    @Provides
    fun provideAppContext(app: Application): Context {
        return app.applicationContext // Aquí estamos usando Application, no ExpenseTrackerApp
    }

    @Provides
    fun provideExpenseDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "expense_database"
        ).build()
    }

    @Provides
    fun provideExpenseDao(database: AppDatabase): ExpenseDao {
        return database.expenseDao()
    }
}
