package com.github.htdangkhoa.demo.ui.todo_viewmodel.store.global

import com.github.htdangkhoa.kdux.State

interface GlobalState: State {
    val isLoading: Boolean
}