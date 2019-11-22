package com.github.htdangkhoa.demo.ui.counter

import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Reducer

class CounterReducer: Reducer<CounterState> {
    override fun reduce(state: CounterState, action: Action): CounterState {
        return when (action) {
            is CounterAction.INCREASE -> {
                state.copy(number = state.number + 1)
            }
            is CounterAction.DECREASE -> {
                state.copy(number = state.number - 1)
            }
            else -> state
        }
    }
}