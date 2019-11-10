package com.github.htdangkhoa.kdux.devtool

import com.github.htdangkhoa.kdux.Action

sealed class KDuxDevToolAction: Action {
    object UNDO: KDuxDevToolAction()

    object REDO: KDuxDevToolAction()

    object RESET: KDuxDevToolAction()
}