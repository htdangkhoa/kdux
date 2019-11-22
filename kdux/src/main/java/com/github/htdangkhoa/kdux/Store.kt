package com.github.htdangkhoa.kdux

import com.github.htdangkhoa.kdux.middlewares.logger.KduxLogger

class Store<S: State>(
    val reducer: Reducer<S>,
    val initialState: S,
    val middlewares: MutableList<Middleware<S>> = mutableListOf()
) {
    private val dispatchers = mutableListOf<Dispatcher>()

    private val enhancers = mutableListOf<Enhancer<S>>()

    var state = initialState
    private set(value) {
        field = value
        enhancers.forEach { it.enhance(field) }
    }

    init {
        dispatchers.add(object: Dispatcher {
            override fun dispatch(action: Action) {
                val newState = reducer.reduce(state, action)

                if (state != newState) {
                    state = newState
                }
            }
        })

        /* --- MAKE SURE THE LOGGER IS ALWAYS LAST ON THE LIST --- */
        var indexOfLogger = -1

        middlewares.forEachIndexed { index, middleware ->
            if (middleware::class.java == KduxLogger::class.java) {
                indexOfLogger = index
            }
        }

        if (indexOfLogger != -1) {
            val logger = middlewares[indexOfLogger]

            middlewares.add(logger)

            middlewares.removeAt(indexOfLogger)
        }

        middlewares.reversed().forEach { middleware ->
            val next = dispatchers.first()

            dispatchers.add(0, object: Dispatcher {
                override fun dispatch(action: Action) {
                    with(middleware) {
                        onBeforeDispatch(this@Store, action)

                        onDispatch(this@Store, action, next)

                        onAfterDispatch(this@Store, action)
                    }
                }
            })
        }
    }

    fun dispatch(action: Action) {
        dispatchers.first().dispatch(action)
    }

    fun subscribe(enhancer: Enhancer<S>) {
        enhancers.add(enhancer)

        enhancer.enhance(state)
    }

    fun unsubscribe(enhancer: Enhancer<S>) {
        enhancers.remove(enhancer)
    }
}