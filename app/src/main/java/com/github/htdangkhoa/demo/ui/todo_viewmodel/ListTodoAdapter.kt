package com.github.htdangkhoa.demo.ui.todo_viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.demo.R
import com.github.htdangkhoa.demo.model.TodoModel

typealias OnTodoRemoveListener = (todoModel: TodoModel) -> Unit

class ListTodoAdapter(
    private val todos: MutableList<TodoModel>
): RecyclerView.Adapter<ListTodoAdapter.VH>() {
    private var onItemRemoveListener: OnTodoRemoveListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.item_todo, parent, false)

        return VH(view)
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(todos[position])

    fun onItemRemoveListener(onItemRemoveListener: OnTodoRemoveListener) {
        this.onItemRemoveListener = onItemRemoveListener
    }

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val chkIsDone = itemView.findViewById<CheckBox>(R.id.chkIsDone)

        private val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)

        private val btnRemove = itemView.findViewById<View>(R.id.btnRemove)

        fun bind(model: TodoModel) {
            chkIsDone.apply {
                setOnTouchListener { _, _ -> true }

                isChecked = model.completed
            }

            txtTitle.text = model.title

            btnRemove.setOnClickListener { onItemRemoveListener?.invoke(model) }
        }
    }
}