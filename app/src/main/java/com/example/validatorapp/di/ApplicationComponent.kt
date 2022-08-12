package com.example.validatorapp.di

import com.example.validatorapp.views.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}
