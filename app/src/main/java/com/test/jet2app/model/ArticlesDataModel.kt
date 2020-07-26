package com.test.jet2app.model

import com.google.gson.annotations.SerializedName

data class ArticlesDataModel(
    @SerializedName("id")
    var id: String,
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("comments")
    var comments: Int,
    @SerializedName("likes")
    var likes: Int,
    @SerializedName("media")
    var mediaList: List<MediaDataModel> = mutableListOf(),
    @SerializedName("user")
    var userList: List<UserDataModel> = mutableListOf()
)


data class MediaDataModel(
    @SerializedName("id")
    var id: String,
    @SerializedName("blogId")
    var blogId: String,
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("image")
    var imageurl: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("url")
    var url: String
)

data class UserDataModel(
    @SerializedName("id")
    var id: String,
    @SerializedName("blogId")
    var blogId: String,
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("avatar")
    var avatar: String,
    @SerializedName("lastname")
    var lastname: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("designation")
    var designation: String,
    @SerializedName("about")
    var about: String
)