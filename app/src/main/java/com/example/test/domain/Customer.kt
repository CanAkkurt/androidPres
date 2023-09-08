package com.example.test.domain

data class Customer(
    var id: Int,
    var email: String,
    var name: String,
    var department: Department,
    var education: String,
    var externType: String?,
    var phoneNr: String,
    var backupContactId: Int
)