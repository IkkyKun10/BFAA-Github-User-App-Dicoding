package com.dicoding.latihan.githubuserdicoding.api

import com.dicoding.latihan.githubuserdicoding.raw.UserResponse
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ) : Call<UserResponse>

    @GET("users")
    @Headers("Authorization: token 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")
    fun getListUser(
        @Path("users") users: String
    ) : Call<ArrayList<UserSearch>>
}