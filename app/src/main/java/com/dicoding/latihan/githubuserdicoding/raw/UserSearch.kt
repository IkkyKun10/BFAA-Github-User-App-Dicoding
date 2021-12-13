package com.dicoding.latihan.githubuserdicoding.raw

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserSearch(
    @SerializedName("login")
    val username: String,

    @SerializedName("id")
    val userId: Int,

    @SerializedName("avatar_url")
    var avatar: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("html_url")
    val htmlUrl: String,

) : Parcelable
