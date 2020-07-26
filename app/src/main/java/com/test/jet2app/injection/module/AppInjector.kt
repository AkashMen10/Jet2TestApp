package com.test.jet2app.injection.module


import com.test.jet2app.network.ArticlesApi
import org.koin.dsl.module
import retrofit2.Retrofit

private val retrofit: Retrofit = createNetworkClient("https://5e99a9b1bc561b0016af3540.mockapi.io/")

private val ARTICLE_API: ArticlesApi = retrofit.create(ArticlesApi::class.java)

val networkModule = module {
    single { ARTICLE_API }
}