package com.test.jet2app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesDao {

    @Query("SELECT Count(*) FROM article_table")
    fun getNumberOfData(): Int

    @Query("SELECT articles FROM article_table WHERE id = :pageNumber")
    fun getListByPageNumber(pageNumber: Int): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articleEntity: ArticleEntity)
}