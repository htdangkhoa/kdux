package com.github.htdangkhoa.demo.ui.list

import com.github.htdangkhoa.demo.global.GlobalAction
import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Reducer

class ListReducer: Reducer<ListState> {
    override fun reduce(state: ListState, action: Action): ListState {
        return when(action) {
            is GlobalAction.IS_LOADING -> {
                state.copy(isLoading = action.payload)
            }
            is ListAction.FETCH_TODOS -> {
                state.todos.addAll(action.payload)
                state.copy(
                    exception = action.exception
                )
            }
            else -> state
        }
    }
}