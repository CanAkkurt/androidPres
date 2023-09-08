package com.example.test.network

import com.example.test.database.customer.DatabaseCustomer
import com.example.test.domain.Customer
import com.example.test.domain.Department
import com.example.test.domain.Role
import com.squareup.moshi.Json

// Represents an Account coming from the API
data class ApiCustomer(
    @Json(name = "id")
    var id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "email")
    val email: String,

    @Json(name = "phoneNumber")
    val phoneNr: String,


    )


//data class ApiCustomerDetail(
//    @Json(name = "id")
//    var id: Int,
//
//    @Json(name = "name")
//    val name: String,
//
//    @Json(name = "email")
//    val email: String,
//
//    @Json(name = "phoneNumber")
//    val phoneNr: String,
//
//    @Json(name = "education")
//    var education: String,
//
//    @Json(name = "department")
//    val department: String,
//
//    @Json(name = "externType")
//    val externType: String,
//
//    @Json(name = "backupContactId")
//    val backupContactId: Int,
//)


// Convert a single ApiAccount into a normal domain Account
fun ApiCustomer.asDomainAccount(): Customer {
    return Customer(
        id = id,
        name = name,
        email = email,
        education = "",
        externType = "",
        phoneNr = "",
        department = "".asDomainDepartment(),
        backupContactId = 0
    )
}


// Convert a single ApiAccount into a normal database Account
fun ApiCustomer.asDatabaseCustomer(): DatabaseCustomer {
    return DatabaseCustomer(
        id = id,
        name = name,
        email = email,
        education = "",
        externType = "",
        phoneNr = phoneNr,
        department = "",
        backupContactId = 0
    )
}

// Container that helps us parsing the api response into multiple domain Accounts
data class ApiAccountContainer(
    @Json(name = "body")
    val apiAccounts: List<ApiCustomer>
)

data class ApiCustomerContainer(
    val customers: List<ApiCustomer>
)


// Convert network result into domain Account
fun ApiAccountContainer.asDomainModels(): List<Customer> {
    return apiAccounts.map { it.asDomainAccount() }
}

fun List<ApiCustomer>.asDomainModels(): List<Customer> {
    return this.map { it.asDomainAccount() }
}

// Convert a list of ApiAccounts into a list of DatabaseAccounts (this can then be used in the insert call as vararg)
fun List<ApiCustomer>.asDatabaseCustomer(): Array<DatabaseCustomer> {
    return map { it.asDatabaseCustomer() }.toTypedArray()
}


// Convert network result into DatabaseCustomer
fun ApiAccountContainer.asDatabaseCustomer(): List<DatabaseCustomer> {
    return apiAccounts.map { it.asDatabaseCustomer() }
}

fun String.asDomainRole(): Role {
    if (lowercase().equals("admin")) {
        return Role.Admin
    } else if (lowercase().equals("manager")) {
        return Role.Manager
    } else if (lowercase().equals("Viewer")) {
        return Role.Viewer
    }
    return Role.Customer

}

fun String.asDomainDepartment(): Department {
    if (lowercase().equals("dbo")) {
        return Department.DBO
    } else if (lowercase().equals("dbt")) {
        return Department.DBT
    } else if (lowercase().equals("dgz")) {
        return Department.DGZ
    } else if (lowercase().equals("dit")) {
        return Department.DIT
    } else if (lowercase().equals("dlo")) {
        return Department.DLO
    } else if (lowercase().equals("dog")) {
        return Department.DOG
    } else if (lowercase().equals("dsa")) {
        return Department.DSA
    }
    return Department.Extern

}