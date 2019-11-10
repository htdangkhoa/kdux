package com.github.htdangkhoa.kdux

interface Enhancer<S: State> {
    fun enhance(state: S)
}