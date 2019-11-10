package com.github.htdangkhoa.demo.ui.list

import com.github.htdangkhoa.demo.global.GlobalState
import com.github.htdangkhoa.demo.model.TodoModel

data class ListState(
    override val isLoading: Boolean = false,
    val todos: MutableList<TodoModel> = mutableListOf(),
    val exception: Exception? = null
): GlobalState