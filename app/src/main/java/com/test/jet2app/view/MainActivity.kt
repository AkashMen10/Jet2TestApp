package com.test.jet2app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.jet2app.R
import com.test.jet2app.viewmodel.ArticlesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var articlesAdapter: ArticlesAdapter = ArticlesAdapter(arrayListOf())
    var isLoading: Boolean = false
    var pageNumber: Int = 1
    private val mArticlesViewModel: ArticlesViewModel by viewModel()
    private val PAGES = 6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        mArticlesViewModel.getArticles(pageNumber)
        observeData()
    }

    private fun initUI() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articlesAdapter
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val itemCount = this@apply.layoutManager?.itemCount ?: 0
                    val lastVisibleItem =
                        (this@apply.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (!isLoading && pageNumber != PAGES
                        && itemCount == lastVisibleItem + 1
                    ) {
                        mArticlesViewModel.getArticles(pageNumber)
                    } else if (pageNumber == PAGES && itemCount == lastVisibleItem + 1)
                        Toast.makeText(
                            this@MainActivity,
                            "Hurray! All caught up",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            })
        }
    }

    private fun observeData() {
        mArticlesViewModel.saveResponseLiveData.observe(this, Observer { articlesList ->
            articlesAdapter.articlesDataModels = articlesList
            pageNumber++
        })

        mArticlesViewModel.progressBarLiveData.observe(this, Observer { it ->
            if (it == 0) {
                progressBar.visibility = View.VISIBLE
                this.isLoading = true
            } else {
                progressBar.visibility = View.GONE
                this.isLoading = false
            }
        })

        mArticlesViewModel.errorMessage.observe(this, Observer { error ->
            Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
        })
    }
}
