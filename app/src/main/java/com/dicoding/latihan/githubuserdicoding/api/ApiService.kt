package com.dicoding.latihan.githubuserdicoding.api

import com.dicoding.latihan.githubuserdicoding.raw.UserDetailResponse
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

    @GET("{users}")
    @Headers("Authorization: token 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")
    fun getListUser(
        @Path("users") users: String
    ) : Call<ArrayList<UserSearch>>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun geDetailUser(
        @Path("username") username: String
    ) : Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(
        @Path("username") username: String
    ) : Call<ArrayList<UserSearch>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUser(
        @Path("username") username: String
    ) : Call<ArrayList<UserSearch>>

}