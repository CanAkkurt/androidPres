package com.example.test.domain

data class Member(
    var id: Int,
    var name: String,
    var email: String,
    var department: Department,
    var role: Role,
)