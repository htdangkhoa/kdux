package com.github.htdangkhoa.demo.ui.counter_thunk

import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Dispatch
import com.github.htdangkhoa.kdux.Dispatcher
import com.github.htdangkhoa.kdux.State
import com.github.htdangkhoa.kdux.middlewares.thunk.KduxThunkAction
import com.github.htdangkhoa.kdux.middlewares.thunk.Thunk

sealed class CounterAction: Action {
    object INCREASE: CounterAction()

    object DECREASE: CounterAction()

    companion object {
        fun increaseAction(): Action = KduxThunkAction(object: Thunk {
            override fun invoke(dispatcher: Dispatcher, state: State) {
                dispatcher.dispatch(INCREASE)
            }
        })

        fun decreaseAction() = KduxThunkAction(object: Thunk {
            override fun invoke(dispatcher: Dispatcher, state: State) {
                dispatcher.dispatch(DECREASE)
            }
        })
    }
}