package com.example.validatorapp

import android.app.Application
import com.example.validatorapp.di.ApplicationComponent
import com.example.validatorapp.di.DaggerApplicationComponent


class MyApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}