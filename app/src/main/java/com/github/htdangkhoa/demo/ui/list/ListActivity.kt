package com.github.htdangkhoa.demo.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.htdangkhoa.demo.R
import com.github.htdangkhoa.kdux.Enhancer
import com.github.htdangkhoa.kdux.Store
import com.github.htdangkhoa.kdux.applyMiddleware
import com.github.htdangkhoa.kdux.composeWithDevTools
import com.github.htdangkhoa.kdux.devtool.KDuxDevToolAction
import com.github.htdangkhoa.kdux.logger.KduxLogger
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity: AppCompatActivity(), Enhancer<ListState> {
    private val store: Store<ListState> by lazy {
        composeWithDevTools(
            Store(
                ListReducer(),
                ListState(),
                applyMiddleware(KduxLogger())
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        btnFetch.setOnClickListener {
            ListAction.fetchTodosAction {
                store.dispatch(
                    it
                )
            }
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

    override fun onStop() {
        super.onStop()

        store.unsubscribe(this)
    }

    override fun enhance(state: ListState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
    }
}
