package com.cornershopapp.core.domain

import com.cornershopapp.core.data.DeviceResourceDataSource

class ResourceRepository(deviceResources: DeviceResourceDataSource) {
    val connectionErrorString = deviceResources.connectionErrorMessage
    val defaultErrorMessageString = deviceResources.defaultErrorMessageString
}