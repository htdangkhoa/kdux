package com.github.htdangkhoa.kdux.middlewares.logger

import android.util.Log
import com.github.htdangkhoa.kdux.*
import com.github.htdangkhoa.kdux.devtool.KduxDevToolAction

class KduxLogger<S: State>: Middleware<S> {
    private val ignores = mutableListOf<Action>(
        KduxDevToolAction.UNDO,
        KduxDevToolAction.RESET,
        KduxDevToolAction.REDO
    )

    private val TAG = "\u200B"

    override fun onBeforeDispatch(store: Store<S>, action: Action) {
        if (ignores.contains(action)) return

        Log.d(TAG, "▼ @@KDUX_LOGGER_${action.getName()}")
        Log.d(TAG, "│    prev state:  ${store.state}")
    }

    override fun onDispatch(store: Store<S>, action: Action, next: Dispatcher) {
        if (!ignores.contains(action)) {
            Log.d(TAG, "│    dispatching: ${action.getName()}")
        }

        next.dispatch(action)
    }

    override fun onAfterDispatch(store: Store<S>, action: Action) {
        if (ignores.contains(action)) return

        Log.d(TAG, "│    next state:  ${store.state}")
    }
}