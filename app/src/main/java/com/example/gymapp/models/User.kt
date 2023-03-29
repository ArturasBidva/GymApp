package com.example.gymapplication.models

data class User(
    val id: Long,
    val name: String,
    val surname: String,
    val username: String,
    val imgUrl: String,
    val password: String
) {
    constructor(username: String, password: String) : this(
        id = 0L,
        name = "",
        surname = "",
        username = username,
        imgUrl = "",
        password = password
    )
}
