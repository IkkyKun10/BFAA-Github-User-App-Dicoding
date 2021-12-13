package com.dicoding.latihan.githubuserdicoding.raw.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserFavorite::class], version = 1)
abstract class UserFavoriteDatabase : RoomDatabase() {
    abstract fun userFavoriteDao() : UserFavoriteDao

    companion object {

        @Volatile
        private var INSTANCE: UserFavoriteDatabase? = null

        fun getInstance(context: Context) : UserFavoriteDatabase? {
            if (INSTANCE == null) {
                synchronized(UserFavoriteDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            UserFavoriteDatabase::class.java,
                            "user_favorite"
                        ).build()
                    }
                }
            }
            return INSTANCE as UserFavoriteDatabase
        }
    }
}