package com.github.htdangkhoa.kdux.middlewares.thunk

import com.github.htdangkhoa.kdux.*

class KduxThunk<S: State>: Middleware<S> {
    override fun onBeforeDispatch(store: Store<S>, action: Action) = Unit

    override fun onDispatch(store: Store<S>, action: Action, next: Dispatcher) {
        when (action) {
            is KduxThunkAction -> {
                action.body(next, store.state)
            }
            else -> next.dispatch(action)
        }
    }

    override fun onAfterDispatch(store: Store<S>, action: Action) = Unit
}