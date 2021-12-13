package com.dicoding.latihan.githubuserdicoding.raw.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM user_favorite")
    fun getFavoriteUser() : LiveData<List<UserFavorite>>

    @Insert
    fun insertFavorite(userFavorite: UserFavorite)

    @Query("SELECT count(*) FROM user_favorite WHERE userId = :id")
    fun checkUser(id: Int) : Int

    @Query("DELETE FROM user_favorite WHERE userId = :id")
    fun deleteById(id: Int) : Int
}