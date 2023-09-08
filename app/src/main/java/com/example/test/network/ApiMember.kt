package com.example.test.network

import com.example.test.database.member.DatabaseMember
import com.example.test.domain.Member
import com.squareup.moshi.Json

// Represents an Member coming from the API
data class ApiMember(
    @Json(name = "id")
    var id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "email")
    val email: String,

//    @Json(name = "phone_nr")
//    val phoneNr: String,

//    @Json(name = "education")
//    val education: String,

    @Json(name = "department")
    val department: String,
//
//    @Json(name = "extern_type")
//    val externType : String?,

    @Json(name = "role")
    val role: String,

//    @Json(name = "member_active")
//    val active : String //?

)

// Convert a single ApiMember into a normal domain Member
fun ApiMember.asDomainMember(): Member {
    return Member(
        id = id,
        name = name,
        email = email,
//        phoneNr = phoneNr,
        role = role.asDomainRole(),
        department = department.asDomainDepartment()
//        active = active.asDomainBoolean()
    )
}


// Convert a single ApiMember into a normal databaseMember
fun ApiMember.asDatabaseMember(): DatabaseMember {
    return DatabaseMember(
        id = id,
        name = name,
        email = email,
//        phoneNr = phoneNr,
        role = role,
        department = department
//        active = active
    )
}

// Container that helps us parsing the api response into multiple domain Members
data class ApiMemberContainer(
    val apiMembers: List<ApiMember>
)

data class ApiMemberContainers(
    val members: List<ApiMember>
)

// Convert network result into domain Members
fun ApiMemberContainer.asDomainModels(): List<Member> {
    return apiMembers.map { it.asDomainMember() }
}

fun List<ApiMember>.asDomainModels(): List<Member> {
    return this.map { it.asDomainMember() }
}

// Convert a list of ApiMembers into a list of DatabaseMembers (this can then be used in the insert call as vararg)
fun List<ApiMember>.asDatabaseMember(): Array<DatabaseMember> {
    return map { it.asDatabaseMember() }.toTypedArray()
}


// Convert network result into DatabaseCustomer
fun ApiMemberContainer.asDatabaseMember(): List<DatabaseMember> {
    return apiMembers.map { it.asDatabaseMember() }
}
