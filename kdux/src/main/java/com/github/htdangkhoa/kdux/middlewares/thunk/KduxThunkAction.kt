package com.github.htdangkhoa.kdux.middlewares.thunk

import com.github.htdangkhoa.kdux.Action
import com.github.htdangkhoa.kdux.Dispatcher
import com.github.htdangkhoa.kdux.State

typealias Thunk = (dispatcher: Dispatcher, state: State) -> Unit

data class KduxThunkAction(val body: Thunk): Action