package com.github.htdangkhoa.kdux

interface Action {
    fun getName(): String = this::class.java.simpleName
}