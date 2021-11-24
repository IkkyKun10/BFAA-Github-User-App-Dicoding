package com.dicoding.latihan.githubuserdicoding.raw

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("items")
    val items: ArrayList<UserSearch>
    )
