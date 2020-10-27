package com.cornershopapp.jokes.domain.model

data class Joke(val id: String,
                val firstLine: String,
                val secondLine: String,
                val category: String)