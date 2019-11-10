package com.github.htdangkhoa.demo.global

import com.github.htdangkhoa.kdux.State

interface GlobalState: State {
    val isLoading: Boolean
}