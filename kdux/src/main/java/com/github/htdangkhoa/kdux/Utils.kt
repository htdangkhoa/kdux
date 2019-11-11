package com.github.htdangkhoa.kdux

import com.github.htdangkhoa.kdux.devtool.KDuxDevToolReducer

typealias Dispatch = (action: Action) -> Unit

inline fun <reified S: State> combineReducers(vararg reducers: Reducer<S>): Reducer<S> {
    return object: Reducer<S> {
        override fun reduce(state: S, action: Action): S {
            var s = state

            reducers.forEach {
                s = it.reduce(s, action)
            }

            return s
        }

    }
}

inline fun <reified S: State> applyMiddleware(vararg middlewares: Middleware<S>): MutableList<Middleware<S>> {
    val list = mutableListOf<Middleware<S>>()

    list.addAll(middlewares)

    return list
}

inline fun <reified S: State> composeWithDevTools(store: Store<S>): Store<S> {
    val initialState = store.initialState

    val reducer = combineReducers(
        store.reducer,
        KDuxDevToolReducer(initialState)
    )

    val middlewares = store.middlewares

    return Store(reducer, initialState, middlewares)
}