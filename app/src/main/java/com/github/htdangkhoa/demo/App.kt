package com.github.htdangkhoa.demo

import android.app.Application
import com.github.kittinunf.fuel.core.FuelManager

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        FuelManager.instance.basePath = "https://jsonplaceholder.typicode.com"
    }
}