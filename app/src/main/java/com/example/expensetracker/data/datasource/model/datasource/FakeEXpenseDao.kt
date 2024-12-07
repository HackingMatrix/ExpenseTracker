import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expensetracker.data.datasource.model.datasource.ExpenseDao
import com.example.expensetracker.data.datasource.model.model.Expense

class FakeExpenseDao : ExpenseDao {
    private val expenses = MutableLiveData<List<Expense>>()

    init {
        // Inicializamos con algunos datos simulados
        expenses.value = listOf(
            Expense(name = "Fake Expense 1", amount = 100.0, date = System.currentTimeMillis()),
            Expense(name = "Fake Expense 2", amount = 50.0, date = System.currentTimeMillis())
        )
    }

    // Implementación del método suspendido insertExpense
    override suspend fun insertExpense(expense: Expense) {
        // Simulamos la inserción de un gasto (no se realiza realmente)
        println("Simulated insert of expense: $expense")
    }

    // Simulamos el método getAllExpenses y devolvemos LiveData
    override fun getAllExpenses(): LiveData<List<Expense>> {
        // Devolvemos el LiveData simulado
        return expenses
    }

    // Implementación del método suspendido deleteExpense
    override suspend fun deleteExpense(expenseId: Long) {
        // Simulamos la eliminación (no se realiza realmente)
        println("Simulated delete of expense with ID: $expenseId")
    }


}
