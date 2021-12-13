package com.dicoding.latihan.githubuserdicoding.raw.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "user_favorite")
data class UserFavorite(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    val userId : Int,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "html_url")
    val htmlUri: String
) : Serializable, Parcelable