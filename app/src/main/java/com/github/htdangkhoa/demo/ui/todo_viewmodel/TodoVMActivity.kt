package com.github.htdangkhoa.demo.ui.todo_viewmodel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.demo.R
import com.github.htdangkhoa.demo.model.TodoModel
import com.github.htdangkhoa.demo.ui.todo_viewmodel.addtodo.AddTodoActivity
import com.github.htdangkhoa.demo.ui.todo_viewmodel.store.TodoState
import com.github.htdangkhoa.kdux.Enhancer
import kotlinx.android.synthetic.main.activity_list_todo.*

class TodoVMActivity: AppCompatActivity(), Enhancer<TodoState> {
    companion object {
        const val REQUEST_ADD_TODO = 999
    }

    private  val viewModel: TodoViewModel by lazy {
        ViewModelProviders.of(this).get(TodoViewModel::class.java)
    }

    private val todos: MutableList<TodoModel> = mutableListOf()

    private val adapter = ListTodoAdapter(todos).apply {
        onItemRemoveListener { todo ->
            viewModel.removeTodoAction(todo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_todo)

        viewModel.loadTodos()

        with(recyclerView) {
            adapter = this@TodoVMActivity.adapter

            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                private val HIDE_THRESHOLD = 20

                private var scrolledDistance = 0

                private var controlsVisible = true

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                        val lp = btnCreate.layoutParams as ConstraintLayout.LayoutParams
                        val fabBottomMargin = lp.bottomMargin
                        btnCreate.animate().translationY((btnCreate.height + fabBottomMargin).toFloat())
                            .setInterpolator(AccelerateInterpolator(2f)).start()

                        controlsVisible = false

                        scrolledDistance = 0
                    } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                        btnCreate.animate().translationY(0F).setInterpolator(DecelerateInterpolator(2F)).start()

                        controlsVisible = true

                        scrolledDistance = 0
                    }

                    if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
                        scrolledDistance += dy
                    }
                }
            })
        }

        btnCreate.setOnClickListener {
            startActivityForResult(
                Intent(this, AddTodoActivity::class.java)
                    .apply {
                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    },
                REQUEST_ADD_TODO
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_ADD_TODO -> {
                    val todo = data?.getParcelableExtra<TodoModel>(AddTodoActivity.TODO_EXTRA)

                    todo?.let {
                        viewModel.addTodoAction(it)
                    }
                }
                else -> {
                    super.onActivityResult(requestCode, resultCode, data)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.subscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.unsubscribe(this)
    }

    override fun enhance(state: TodoState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        val cb = TodoModel.DiffCallback(todos, state.todos)

        val diffResult = DiffUtil.calculateDiff(cb)

        diffResult.dispatchUpdatesTo(adapter)

        todos.clear()
        todos.addAll(state.todos)
    }
}
