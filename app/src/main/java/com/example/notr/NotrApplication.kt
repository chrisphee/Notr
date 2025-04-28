package com.example.notr

import android.app.Application
import com.example.notr.data.AppContainer
import com.example.notr.data.AppDataContainer
import com.example.notr.data.AppDatabase

class NotrApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}