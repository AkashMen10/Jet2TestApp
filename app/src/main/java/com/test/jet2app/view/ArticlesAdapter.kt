package com.test.jet2app.view

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.test.jet2app.R
import com.test.jet2app.model.ArticlesDataModel
import com.test.jet2app.model.MediaDataModel
import com.test.jet2app.model.UserDataModel
import com.test.jet2app.utils.getNumberInDisplayFormat
import com.test.jet2app.utils.publishedAgo
import kotlinx.android.synthetic.main.items_layout.view.*

class ArticlesAdapter(articlesDataModels: ArrayList<ArticlesDataModel>) :
    RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    var articlesDataModels: ArrayList<ArticlesDataModel> = arrayListOf()
        set(value) {
            articlesDataModels.addAll(value)
            notifyDataSetChanged()
        }

    init {
        this.articlesDataModels = articlesDataModels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false))


    override fun getItemCount(): Int = articlesDataModels.size


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articlesDataModels.get(position))
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(articlesDataModel: ArticlesDataModel) {
            val userDataModel: UserDataModel? = if (articlesDataModel.userList.isNotEmpty()) articlesDataModel.userList.get(0) else null
            val mediaDataModel: MediaDataModel? = if (articlesDataModel.mediaList.isNotEmpty()) articlesDataModel.mediaList.get(0) else null

            val requestOption = RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(itemView.context)
                .load(userDataModel?.avatar)
                .apply(requestOption)
                .into(itemView.image_view)

            if (mediaDataModel?.imageurl.isNullOrBlank())
                itemView.article_iv.visibility = GONE
            else {
                itemView.article_iv.visibility = VISIBLE
                Glide.with(itemView.context)
                    .load(mediaDataModel?.imageurl)
                    .apply(requestOption)
                    .into(itemView.article_iv)
            }
            itemView.user_name_tv.text = "${userDataModel?.name} ${userDataModel?.lastname}"
            itemView.user_designation_tv.text = userDataModel?.designation
            itemView.duration_tv.text = publishedAgo(itemView.context, articlesDataModel.createdAt)

            if (mediaDataModel?.title.isNullOrBlank())
                itemView.title_tv.visibility = GONE
            else {
                itemView.title_tv.visibility = VISIBLE
                itemView.title_tv.text = mediaDataModel?.title
            }
            itemView.likes_tv.text = articlesDataModel.likes.getNumberInDisplayFormat(
                itemView.context,
                itemView.context.getString(R.string.like_string)
            )
            itemView.comments_tv.text = articlesDataModel.comments.getNumberInDisplayFormat(
                itemView.context,
                itemView.context.getString(R.string.comment_string)
            )
            if (articlesDataModel.content.isBlank())
                itemView.content_tv.visibility = GONE
            else {
                itemView.content_tv.visibility = VISIBLE
                itemView.content_tv.text = articlesDataModel.content
            }
            if (mediaDataModel?.url.isNullOrBlank())
                itemView.url_tv.visibility = GONE
            else {
                itemView.url_tv.visibility = VISIBLE
                itemView.url_tv.text = mediaDataModel?.url
            }
        }
    }
}