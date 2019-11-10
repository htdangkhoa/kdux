package com.github.htdangkhoa.demo.ui.list

import com.github.htdangkhoa.demo.global.GlobalAction
import com.github.htdangkhoa.demo.model.TodoModel
import com.github.htdangkhoa.kdux.Action
import com.github.kittinunf.fuel.gson.gsonDeserializer
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.lang.Exception

sealed class ListAction: Action {
    data class FETCH_TODOS(
        val payload: MutableList<TodoModel> = mutableListOf(),
        val exception: Exception? = null
    ): ListAction()

    companion object: ListAction() {
        fun fetchTodosAction(dispatch: (action: Action) -> Unit) {
            GlobalAction.updateLoadingAction(true, dispatch)

            "/todos".httpGet().responseObject(gsonDeserializer<MutableList<TodoModel>>()) { _, _, result ->
                GlobalAction.updateLoadingAction(false, dispatch)

                when (result) {
                    is Result.Success -> {
                        dispatch(
                            FETCH_TODOS(
                                payload = result.get()
                            )
                        )
                    }
                    is Result.Failure -> {
                        dispatch(
                            FETCH_TODOS(
                                exception = result.getException()
                            )
                        )
                    }
                }
            }.join()
        }
    }
}