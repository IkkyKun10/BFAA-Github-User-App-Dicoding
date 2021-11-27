package com.dicoding.latihan.githubuserdicoding.raw

import com.google.gson.annotations.SerializedName



data class UserDetailResponse (
    @SerializedName("id")
    val userId: Int,

    @SerializedName("login")
    val username: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("avatar_url")
    val avatar: String,

    @SerializedName("company")
    val company: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("public_repos")
    val repository: Int,

    @SerializedName("followers")
    val follower: Int,

    @SerializedName("following")
    val following: Int
    )
