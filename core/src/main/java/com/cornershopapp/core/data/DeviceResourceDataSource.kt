package com.cornershopapp.core.data

interface DeviceResourceDataSource {
    val connectionErrorMessage : String
    val defaultErrorMessageString : String
}

object DeviceResourceDataSourceImp : DeviceResourceDataSource {
    override val connectionErrorMessage = "Connection error"
    override val defaultErrorMessageString = "There has been a problem"
}