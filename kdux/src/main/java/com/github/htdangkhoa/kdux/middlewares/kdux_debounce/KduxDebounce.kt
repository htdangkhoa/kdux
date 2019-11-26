package com.github.htdangkhoa.kdux.middlewares.kdux_debounce

import android.os.Handler
import androidx.annotation.IntRange
import com.github.htdangkhoa.kdux.*

class KduxDebounce<S: State>(
    @IntRange(from = 0, to = 30000) val debounce: Long = 0
): Middleware<S> {
    private val actions = mutableListOf<Action>()

    private val handler = Handler()

    private var runner: Runnable? = null

    override fun onBeforeDispatch(store: Store<S>, action: Action) {
        actions.add(action)
    }

    override fun onDispatch(store: Store<S>, action: Action, next: Dispatcher) {
        runner?.let { handler.removeCallbacks(it) }

        runner = Runnable {
            if (actions.size != 0) {
                val a = actions.last()

                next.dispatch(a)
            }

            actions.clear()
        }

        if (debounce == 0L) {
            runner?.run()
        } else {
            runner?.let {
                handler.postDelayed(it, debounce)
            }
        }
    }

    override fun onAfterDispatch(store: Store<S>, action: Action) = Unit
}