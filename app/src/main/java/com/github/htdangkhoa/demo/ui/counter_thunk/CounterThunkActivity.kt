package com.github.htdangkhoa.demo.ui.counter_thunk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.htdangkhoa.demo.R
import com.github.htdangkhoa.kdux.Enhancer
import com.github.htdangkhoa.kdux.Store
import com.github.htdangkhoa.kdux.applyMiddleware
import com.github.htdangkhoa.kdux.middlewares.thunk.KduxThunk
import kotlinx.android.synthetic.main.activity_counter_thunk.*

class CounterThunkActivity: AppCompatActivity(), Enhancer<CounterState> {
    private val store = Store(
        CounterReducer(),
        CounterState(),
        applyMiddleware(KduxThunk())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter_thunk)

        btnIncrease.setOnClickListener {
            store.dispatch(CounterAction.increaseAction())
        }

        btnDecrease.setOnClickListener {
            store.dispatch(CounterAction.decreaseAction())
        }
    }

    override fun onStart() {
        super.onStart()

        store.subscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        store.unsubscribe(this)
    }

    override fun enhance(state: CounterState) {
        txtNumber.text = "${state.number}"
    }
}
