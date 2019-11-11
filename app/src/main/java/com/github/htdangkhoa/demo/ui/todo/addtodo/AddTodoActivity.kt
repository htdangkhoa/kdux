package com.github.htdangkhoa.demo.ui.todo.addtodo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.htdangkhoa.demo.R
import com.github.htdangkhoa.demo.model.TodoModel
import kotlinx.android.synthetic.main.activity_add_todo.*

class AddTodoActivity : AppCompatActivity() {
    companion object {
        const val TODO_EXTRA = "@@TODO_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        btnDone.setOnClickListener {
            val todo = TodoModel(
                id = 999,
                title = edtTitle.text.toString()
            )

            val intent = Intent().apply {
                putExtra(TODO_EXTRA, todo)
            }
            setResult(Activity.RESULT_OK, intent)

            supportFinishAfterTransition()
        }
    }
}
