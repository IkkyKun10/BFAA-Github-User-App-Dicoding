package com.dicoding.latihan.githubuserdicoding.viewmodel

import java.io.InputStreamReader

class MockResponseFileReader(path: String) {

    private val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}