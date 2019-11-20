package com.github.htdangkhoa.demo.ui.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {
    val job = SupervisorJob()

    val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun launch(callback: suspend CoroutineScope.() -> Unit) = uiScope.launch {
        callback(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        uiScope.coroutineContext.cancelChildren()
    }
}