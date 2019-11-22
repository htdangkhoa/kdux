package com.github.htdangkhoa.demo.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.htdangkhoa.demo.R
import com.github.htdangkhoa.demo.ui.counter.CounterActivity
import com.github.htdangkhoa.demo.ui.todo.ListTodoActivity
import com.github.htdangkhoa.demo.ui.todo_viewmodel.TodoVMActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGoToCounter.setOnClickListener {
            startActivity(
                Intent(this, CounterActivity::class.java)
                    .apply {
                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    }
            )
        }

        btnGoToToDo.setOnClickListener {
            startActivity(
                Intent(this, ListTodoActivity::class.java)
                    .apply {
                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    }
            )
        }

        btnGoToToDoViewModel.setOnClickListener {
            startActivity(
                Intent(this, TodoVMActivity::class.java)
                    .apply {
                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    }
            )
        }
    }
}
