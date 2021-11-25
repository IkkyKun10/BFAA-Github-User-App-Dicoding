package com.dicoding.latihan.githubuserdicoding.adapter

import com.dicoding.latihan.githubuserdicoding.raw.UserSearch

interface ShareCallback {
    
    fun onShareClick(users: UserSearch)

    //fun onNavDetail(users: UserSearch)
}