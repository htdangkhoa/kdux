package com.github.htdangkhoa.demo.ui.todo.store

import com.github.htdangkhoa.demo.model.TodoModel
import com.github.htdangkhoa.demo.ui.todo.store.global.GlobalAction
import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Reducer

class TodoReducer: Reducer<TodoState> {
    override fun reduce(state: TodoState, action: Action): TodoState {
        val todos = mutableListOf<TodoModel>()
        todos.addAll(state.todos)

        return when(action) {
            is GlobalAction.IS_LOADING -> {
                state.copy(isLoading = action.payload)
            }
            is TodoAction.LOAD_TODOS_SUCCESS -> {
                todos.addAll(action.payload)

                state.copy(todos = todos)
            }
            is TodoAction.LOAD_TODOS_FAILURE -> {
                state.copy(exception = action.payload)
            }
            is TodoAction.ADD_TODO -> {
                todos.add(action.payload)

                state.copy(todos = todos)
            }
            is TodoAction.REMOVE_TODO -> {
                todos.remove(action.payload)

                state.copy(todos = todos)
            }
            else -> state
        }
    }
}