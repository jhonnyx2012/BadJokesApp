package com.cornershopapp.jokes.data.mapper

import com.cornershopapp.jokes.data.dto.DataJoke
import com.cornershopapp.jokes.domain.model.Joke

fun DataJoke.toJoke() : Joke {
    return Joke(id, setup, delivery, category)
}