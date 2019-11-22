package com.github.htdangkhoa.demo.ui.todo_viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import com.github.htdangkhoa.demo.R
import com.github.htdangkhoa.demo.model.TodoModel
import com.github.htdangkhoa.demo.ui.todo_viewmodel.store.TodoState
import com.github.htdangkhoa.kdux.Enhancer
import kotlinx.android.synthetic.main.activity_todo_vm.*

class TodoVMActivity: AppCompatActivity(), Enhancer<TodoState> {
    private  val viewModel: TodoViewModel by lazy {
        ViewModelProviders.of(this).get(TodoViewModel::class.java)
    }

    private val todos: MutableList<TodoModel> = mutableListOf()

    private val adapter = ListTodoAdapter(todos).apply {
        onItemRemoveListener { todo ->
            viewModel.removeTodoAction(todo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_vm)

        with(recyclerView) {
            adapter = this@TodoVMActivity.adapter
        }

        viewModel.loadTodos()
    }

    override fun onStart() {
        super.onStart()

        viewModel.subscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.unsubscribe(this)
    }

    override fun enhance(state: TodoState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        val cb = TodoModel.DiffCallback(todos, state.todos)

        val diffResult = DiffUtil.calculateDiff(cb)

        diffResult.dispatchUpdatesTo(adapter)

        todos.clear()
        todos.addAll(state.todos)
    }
}
