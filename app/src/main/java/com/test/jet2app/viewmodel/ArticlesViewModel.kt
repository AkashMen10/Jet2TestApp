package com.test.jet2app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.jet2app.model.ArticlesDataModel
import com.test.jet2app.repository.ArticlesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class ArticlesViewModel(private val articlesRepository: ArticlesRepository) : ViewModel() {
    private val LIMIT = 10
    val saveResponseLiveData = MutableLiveData<ArrayList<ArticlesDataModel>>()
    val progressBarLiveData = MutableLiveData<Int>()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private lateinit var subscription: Disposable

    fun getArticles(pageNumber: Int) {
        subscription = articlesRepository.getArticlesCount().subscribe(
            { count ->
                getList(pageNumber, count)
            },
            { error ->
                getList(pageNumber, 0)
            }
        )
    }

    private fun getList(pageNumber: Int, dbCount: Int) {
        if (dbCount >= pageNumber) {
            getArticleListFromDataBase(pageNumber)
        } else {
            subscription =
                articlesRepository.getArticles(pageNumber, LIMIT).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { progressBarLiveData.value = 0 }
                    .doOnTerminate { progressBarLiveData.value = 1 }
                    .subscribe(
                        { result ->
                            insertArticlesInDB(pageNumber, result)
                        },
                        { error ->
                            errorMessage.value = error.message
                        }
                    )
        }
    }

    private fun getArticleListFromDataBase(pageNumber: Int) {
        subscription = articlesRepository.getArticlesByPageNumber(pageNumber)
            .doOnSubscribe { progressBarLiveData.value = 0 }
            .doOnTerminate { progressBarLiveData.value = 1 }
            .subscribe({ articleList: ArrayList<ArticlesDataModel> ->
                saveResponseLiveData.value = articleList
            }, { error ->
                errorMessage.value = error.message
            })
    }

    private fun insertArticlesInDB(
        pageNumber: Int,
        articleListToInsert: ArrayList<ArticlesDataModel>
    ) {
        subscription = articlesRepository.insertInDB(pageNumber, articleListToInsert)
            .doOnSubscribe { progressBarLiveData.value = 0 }
            .doOnTerminate { progressBarLiveData.value = 1 }
            .subscribe({ _ ->
                saveResponseLiveData.value = articleListToInsert
            }, { error ->
                errorMessage.value = error.message
            })
    }
}