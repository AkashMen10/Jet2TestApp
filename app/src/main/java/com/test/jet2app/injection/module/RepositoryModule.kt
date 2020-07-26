package com.test.jet2app.injection.module

import com.test.jet2app.repository.ArticlesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ArticlesRepository(articlesApi = get()) }
}