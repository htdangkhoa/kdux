package com.github.htdangkhoa.demo.ui.counter

import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Dispatch

sealed class CounterAction: Action {
    object INCREASE: CounterAction()

    object DECREASE: CounterAction()

    companion object {
        fun increaseAction(dispatch: Dispatch) {
            return dispatch(INCREASE)
        }

        fun decreaseAction(dispatch: Dispatch) {
            return dispatch(DECREASE)
        }
    }
}