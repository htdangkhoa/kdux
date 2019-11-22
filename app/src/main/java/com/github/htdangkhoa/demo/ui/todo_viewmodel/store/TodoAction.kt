package com.github.htdangkhoa.demo.ui.todo_viewmodel.store

import android.util.Log
import com.github.htdangkhoa.demo.model.TodoModel
import com.github.htdangkhoa.demo.ui.todo.store.global.GlobalAction
import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Dispatch
import com.github.kittinunf.fuel.coroutines.awaitObjectResponse
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
        @Deprecated("Should use awaitLoadTodosAction instead of loadTodosAction.")
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

        suspend fun awaitLoadTodosAction(dispatch: Dispatch) {
            GlobalAction.updateLoadingAction(true, dispatch)

            Log.w("awaitLoadTodosAction","Task 1")

            try {
                val (_, _, todos) = "/todos"
                    .httpGet()
                    .awaitObjectResponse(gsonDeserializer<MutableList<TodoModel>>())

                Log.w("awaitLoadTodosAction","Task 2")

                dispatch(LOAD_TODOS_SUCCESS(todos))
            } catch (e: Exception) {
                dispatch(LOAD_TODOS_FAILURE(e))
            }

            Log.w("awaitLoadTodosAction","Task 3")

            GlobalAction.updateLoadingAction(false, dispatch)
        }
    }
}