package com.cornershopapp.core.data

import com.cornershopapp.core.domain.ResourceRepository

object AndroidResourceRepository : ResourceRepository {
    override val connectionErrorString = "Connection error"
    override val defaultErrorMessageString = "There has been a problem"
}