package com.test.jet2app.injection.module

import com.test.jet2app.viewmodel.ArticlesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { ArticlesViewModel(get()) }
}
