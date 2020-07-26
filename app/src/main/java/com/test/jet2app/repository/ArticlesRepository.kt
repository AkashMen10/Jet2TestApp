package com.test.jet2app.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.jet2app.Jet2Application
import com.test.jet2app.database.ArticleEntity
import com.test.jet2app.model.ArticlesDataModel
import com.test.jet2app.network.ArticlesApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class ArticlesRepository(val articlesApi: ArticlesApi) {

    private val articlesDao = Jet2Application.instance!!.getRoomDAO().articleDao()

    fun getArticles(pageNumber: Int, limit: Int): Observable<ArrayList<ArticlesDataModel>> {
        return articlesApi.getArticles(pageNumber, limit)
    }

    fun insertInDB(
        pageNumber: Int,
        articlesDataList: ArrayList<ArticlesDataModel>
    ): Observable<Unit> {
        return Observable.fromCallable {
            articlesDao.insert(
                ArticleEntity(
                    pageNumber,
                    articlesToString(articlesDataList)
                )
            )
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getArticlesCount(): Observable<Int> {
        return Observable.fromCallable { articlesDao.getNumberOfData() }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getArticlesByPageNumber(pageNumber: Int): Observable<ArrayList<ArticlesDataModel>> {
        return Observable.fromCallable {
            articlesDao.getListByPageNumber(pageNumber)
        }.map { articleString: String ->
            jsonToArticles(articleString)
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun articlesToString(articlesList: ArrayList<ArticlesDataModel>): String =
        Gson().toJson(articlesList)

    private fun jsonToArticles(dataString: String): ArrayList<ArticlesDataModel> {
        val listType = object : TypeToken<ArrayList<ArticlesDataModel>>() {}.type
        return Gson().fromJson(dataString, listType)
    }

}