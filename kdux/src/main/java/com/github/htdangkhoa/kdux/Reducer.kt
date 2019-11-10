package com.github.htdangkhoa.kdux

interface Reducer<S: State> {
    fun reduce(state: S, action: Action): S
}