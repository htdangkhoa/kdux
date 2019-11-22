package com.github.htdangkhoa.demo.ui.todo_viewmodel.store.global

import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Dispatch

sealed class GlobalAction: Action {
    data class IS_LOADING(val payload: Boolean = false): GlobalAction()

    companion object {
        fun updateLoadingAction(isLoading: Boolean, dispatch: Dispatch) {
            return dispatch(IS_LOADING(isLoading))
        }
    }
}
