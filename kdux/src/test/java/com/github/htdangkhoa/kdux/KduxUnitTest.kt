package com.github.htdangkhoa.kdux

import org.junit.Assert
import org.junit.Test

data class CounterState(val number: Int = 0): State

sealed class CounterActions: Action {
    object INCREASE: CounterActions()

    object DECREASE: CounterActions()

    companion object {
        fun increaseAction(dispatch: Dispatch) = dispatch(INCREASE)

        fun decreaseAction(dispatch: Dispatch) = dispatch(DECREASE)
    }
}

class CounterReducer: Reducer<CounterState> {
    override fun reduce(state: CounterState, action: Action): CounterState {
        return when (action) {
            CounterActions.INCREASE -> state.copy(number = state.number + 1)

            CounterActions.DECREASE -> state.copy(number = state.number - 1)

            else -> state
        }
    }

}

class KduxUnitTest {
    private val store = Store(
        CounterReducer(),
        CounterState()
    )

    @Test
    fun increase() {
        CounterActions.increaseAction { store.dispatch(it) }

        Assert.assertEquals(store.initialState.number + 1, store.state.number)
    }

    @Test
    fun decrease() {
        CounterActions.decreaseAction { store.dispatch(it) }

        Assert.assertEquals(store.initialState.number - 1, store.state.number)
    }
}