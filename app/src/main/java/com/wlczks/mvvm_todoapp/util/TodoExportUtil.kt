import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.wlczks.mvvm_todoapp.data.Todo
import java.io.File
import java.io.FileOutputStream

fun exportTodosToTxt(context: Context, todos: List<Todo>) {
    val exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val exportFile = File(exportDir, "todos.txt")
    try {
        FileOutputStream(exportFile).use { fileOutputStream ->
            for (todo in todos) {
                val todoText =
                    "${todo.title}\n${todo.description}\n${todo.date}\n${todo.priority}\n${todo.isDone}\n--------------------------\n\n"
                fileOutputStream.write(todoText.toByteArray())
            }
        }
        // success
        println("Todos exported to ${exportFile.absolutePath}")
        Toast.makeText(context, "Todos exported to ${exportFile.absolutePath}", Toast.LENGTH_LONG)
            .show()
    } catch (e: Exception) {
        // fail
        println("Failed to export todos: ${e.message}")
        Toast.makeText(context, "Failed to export todos: ${e.message}", Toast.LENGTH_LONG).show()
    }
}
