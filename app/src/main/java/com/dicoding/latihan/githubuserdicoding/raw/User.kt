package com.dicoding.latihan.githubuserdicoding.raw

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
//import kotlinx.parcelize.Parcelize


//@Parcelize
data class User (
    @SerializedName("id")
    val userId: Int,

    @SerializedName("login")
    val username: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("avatar_url")
    val avatar: Int,

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
    //: Parcelable