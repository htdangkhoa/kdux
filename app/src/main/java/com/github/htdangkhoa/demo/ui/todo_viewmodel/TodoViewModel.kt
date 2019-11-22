package com.github.htdangkhoa.demo.ui.todo_viewmodel

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.demo.model.TodoModel
import com.github.htdangkhoa.demo.ui.todo_viewmodel.store.TodoAction
import com.github.htdangkhoa.demo.ui.todo_viewmodel.store.TodoReducer
import com.github.htdangkhoa.demo.ui.todo_viewmodel.store.TodoState
import com.github.htdangkhoa.kdux.Enhancer
import com.github.htdangkhoa.kdux.Store
import kotlinx.coroutines.*

class TodoViewModel: ViewModel() {
    private val job = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val store = Store(
        TodoReducer(),
        TodoState()
    )

    fun loadTodos() = uiScope.launch {
        TodoAction.awaitLoadTodosAction { store.dispatch(it) }
    }

    fun addTodoAction(todo: TodoModel) =
        TodoAction.addTodoAction(todo) { store.dispatch(it) }

    fun removeTodoAction(todo: TodoModel) =
        TodoAction.removeTodoAction(todo) { store.dispatch(it) }

    fun subscribe(enhancer: Enhancer<TodoState>) {
        store.subscribe(enhancer)
    }

    fun unsubscribe(enhancer: Enhancer<TodoState>) {
        store.unsubscribe(enhancer)
    }

    override fun onCleared() {
        super.onCleared()

        uiScope.coroutineContext.cancelChildren()
    }
}