package com.swvl.tasksearch.di

import com.swvl.tasksearch.retrofit.home.HomeInterface
import com.swvl.tasksearch.retrofit.home.HomeRepository
import com.swvl.tasksearch.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { HomeInterface.create() }
}
val repositoryModule = module {
    single { HomeRepository(get()) }
}
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}