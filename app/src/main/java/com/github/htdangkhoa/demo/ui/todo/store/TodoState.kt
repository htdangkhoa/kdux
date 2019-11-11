package com.github.htdangkhoa.demo.ui.todo.store

import com.github.htdangkhoa.demo.model.TodoModel
import com.github.htdangkhoa.demo.ui.todo.store.global.GlobalState

data class TodoState(
    override val isLoading: Boolean = false,
    val todos: MutableList<TodoModel> = mutableListOf(),
    val exception: Exception? = null
): GlobalState