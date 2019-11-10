package com.github.htdangkhoa.kdux.logger

import android.util.Log
import com.github.htdangkhoa.kdux.*

class KduxLogger<S: State>: Middleware<S> {
    private val TAG = "\u200B"

    override fun onBeforeDispatch(store: Store<S>, action: Action) {
        if ("$action".toUpperCase() != "UNDO" &&
            "$action".toUpperCase() != "REDO" &&
            "$action".toUpperCase() != "RESET") {
            Log.d(TAG, "▼ $action")
            Log.d(TAG, "│    prev state:  ${store.state}")
        }
    }

    override fun onDispatch(store: Store<S>, action: Action, next: Dispatcher) {
        if ("$action".toUpperCase() != "UNDO" &&
            "$action".toUpperCase() != "REDO" &&
            "$action".toUpperCase() != "RESET") {
            Log.d(TAG, "│    dispatching: $action")
        }

        next.dispatch(action)
    }

    override fun onAfterDispatch(store: Store<S>, action: Action) {
        if ("$action".toUpperCase() != "UNDO" &&
            "$action".toUpperCase() != "REDO" &&
            "$action".toUpperCase() != "RESET") {
            Log.d(TAG, "│    next state:  ${store.state}")
        }
    }
}