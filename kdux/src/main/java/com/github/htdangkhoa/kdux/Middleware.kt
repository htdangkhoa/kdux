package com.github.htdangkhoa.kdux

interface Middleware<S: State> {
    fun onBeforeDispatch(store: Store<S>, action: Action)

    fun onDispatch(store: Store<S>, action: Action, next: Dispatcher)

    fun onAfterDispatch(store: Store<S>, action: Action)
}