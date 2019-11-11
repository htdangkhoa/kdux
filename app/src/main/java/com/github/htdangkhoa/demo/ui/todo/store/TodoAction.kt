package com.github.htdangkhoa.demo.ui.todo.store

import com.github.htdangkhoa.demo.model.TodoModel
import com.github.htdangkhoa.demo.ui.todo.store.global.GlobalAction
import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Dispatch
import com.github.kittinunf.fuel.gson.gsonDeserializer
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

sealed class TodoAction: Action {
    data class LOAD_TODOS_SUCCESS(
        val payload: MutableList<TodoModel> = mutableListOf()
    ): TodoAction()

    data class LOAD_TODOS_FAILURE(
        val payload: Exception
    ): TodoAction()

    data class ADD_TODO(
        val payload: TodoModel
    ): TodoAction()

    data class REMOVE_TODO(
        val payload: TodoModel
    ): TodoAction()

    companion object {
        fun loadTodosAction(dispatch: Dispatch) {
            GlobalAction.updateLoadingAction(true, dispatch)

            "/todos".httpGet().responseObject(gsonDeserializer<MutableList<TodoModel>>()) { _, _, result ->
                GlobalAction.updateLoadingAction(false, dispatch)

                when (result) {
                    is Result.Success -> {
                        dispatch(
                            LOAD_TODOS_SUCCESS(
                                result.get()
                            )
                        )
                    }
                    is Result.Failure -> {
                        dispatch(
                            LOAD_TODOS_FAILURE(
                                result.getException()
                            )
                        )
                    }
                }
            }.join()
        }

        fun addTodoAction(todoModel: TodoModel, dispatch: Dispatch) {
            dispatch(ADD_TODO(todoModel))
        }

        fun removeTodoAction(todoModel: TodoModel, dispatch: Dispatch) {
            dispatch(REMOVE_TODO(todoModel))
        }
    }
}