package com.example.test.domain

import java.time.LocalDateTime

data class VirtualMachine(
    var name: String,
    var id: Int,
    var state: State,
    var vcpUAmount: Int,
    var memoryAmount: Int,
    var storageAmount: Int,
    var active: String,
    var vcpUAmountInUse: Int,
    var memoryAmountInUse: Int,
    var storageAmountInUse: Int,
    var fySiekeServer: String? = "N/A",
    var startDate: LocalDateTime,
    var endDate: LocalDateTime
)