package com.github.htdangkhoa.kdux.devtool

import com.github.htdangkhoa.kdux.Action

sealed class KduxDevToolAction: Action {
    object UNDO: KduxDevToolAction()

    object REDO: KduxDevToolAction()

    object RESET: KduxDevToolAction()
}