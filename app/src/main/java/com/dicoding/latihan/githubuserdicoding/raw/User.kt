package com.dicoding.latihan.githubuserdicoding.raw

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User (
    val userId: Int,
    val username: String,
    val name: String,
    val avatar: Int,
    val company: String,
    val location: String,
    val repository: Int,
    val follower: Int,
    val following: Int
    ) : Parcelable