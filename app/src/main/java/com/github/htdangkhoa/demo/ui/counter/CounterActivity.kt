package com.github.htdangkhoa.demo.ui.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.htdangkhoa.demo.R
import com.github.htdangkhoa.kdux.Enhancer
import com.github.htdangkhoa.kdux.Store
import com.github.htdangkhoa.kdux.composeWithDevTools
import com.github.htdangkhoa.kdux.devtool.KDuxDevToolAction
import kotlinx.android.synthetic.main.activity_counter.*

class CounterActivity: AppCompatActivity(), Enhancer<CounterState> {
    private val store: Store<CounterState> by lazy {
        composeWithDevTools(
            Store(
                CounterReducer(),
                CounterState()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        btnIncrease.setOnClickListener {
            CounterAction.increaseAction { store.dispatch(it) }
        }

        btnDecrease.setOnClickListener {
            CounterAction.decreaseAction { store.dispatch(it) }
        }

        btnUndo.setOnClickListener {
            store.dispatch(KDuxDevToolAction.UNDO)
        }

        btnRedo.setOnClickListener {
            store.dispatch(KDuxDevToolAction.REDO)
        }

        btnReset.setOnClickListener {
            store.dispatch(KDuxDevToolAction.RESET)
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
