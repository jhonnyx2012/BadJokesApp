package com.cornershopapp.jokes.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataJoke(val category: String,
                    val setup: String,
                    val delivery: String,
                    val id: String)