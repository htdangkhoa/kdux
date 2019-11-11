package com.github.htdangkhoa.demo.ui.todo.store.global

import com.github.htdangkhoa.kdux.Action

sealed class GlobalAction: Action {
    data class IS_LOADING(val payload: Boolean = false): GlobalAction()

    companion object {
        fun updateLoadingAction(isLoading: Boolean, dispatch: (action: Action) -> Unit) {
            return dispatch(IS_LOADING(isLoading))
        }
    }
}
