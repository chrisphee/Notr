package com.example.notr

import android.app.Application
import com.example.notr.data.AppDatabase

class NotrApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}
}