package com.test.jet2app.network

import com.test.jet2app.model.ArticlesDataModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesApi {

    @GET("/jet2/api/v1/blogs")
    fun getArticles(@Query("page") pageNumber:Int, @Query("limit") limit:Int) :Observable<ArrayList<ArticlesDataModel>>
}