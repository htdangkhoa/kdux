package com.github.htdangkhoa.kdux.devtool

import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Reducer
import com.github.htdangkhoa.kdux.State

class KDuxDevToolReducer<S: State>(private val initialState: S):
    Reducer<S> {
    private var currentStateIndex = -1

    private val stateTimeLines = mutableListOf(initialState)

    private var present: S = initialState
        get() = stateTimeLines.getOrNull(currentStateIndex) ?: initialState

    override fun reduce(state: S, action: Action): S {
        when(action) {
            is KDuxDevToolAction.UNDO -> {
                currentStateIndex -= 1

                if (currentStateIndex < 0) {
                    currentStateIndex = 0

                    return present
                }

                present = stateTimeLines[currentStateIndex]
            }
            is KDuxDevToolAction.REDO -> {
                currentStateIndex += 1

                if (currentStateIndex > stateTimeLines.size - 1) {
                    currentStateIndex = stateTimeLines.lastIndex

                    return present
                }

                present = stateTimeLines[currentStateIndex]
            }
            is KDuxDevToolAction.RESET -> {
                currentStateIndex = -1

                present = initialState

                stateTimeLines.clear().also { stateTimeLines.add(present) }
            }
            else -> {
                present = state

                stateTimeLines.add(state)

                currentStateIndex = stateTimeLines.lastIndex
            }
        }

        return present
    }
}