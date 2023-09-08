package com.example.test

import com.example.test.database.customer.asDomainDepartment
import com.example.test.database.customer.asDomainRole
import com.example.test.domain.Member
import org.junit.Test

class MemberTest {


    @Test
    fun testGetters() {
        val member = Member(
            id = 1,
            name = "John Doe",
            email = "johndoe@example.com",
            department = "Sales".asDomainDepartment(),
            role = "Manager".asDomainRole()
        )

        // Test getters
        assert(member.id == 1)
        assert(member.name == "John Doe")
        assert(member.email == "johndoe@example.com")
        assert(member.department == "Sales".asDomainDepartment())
        assert(member.role == "Manager".asDomainRole())
    }

    @Test
    fun testSetters() {
        val member = Member(
            id = 1,
            name = "John Doe",
            email = "johndoe@example.com",
            department = "Sales".asDomainDepartment(),
            role = "Manager".asDomainRole()
        )

        // Test setters
        member.id = 2
        member.name = "Jane Smith"
        member.email = "janesmith@example.com"
        member.department = "Sales".asDomainDepartment()
        member.role = "Supervisor".asDomainRole()

        assert(member.id == 2)
        assert(member.name == "Jane Smith")
        assert(member.email == "janesmith@example.com")
        assert(member.department == "Sales".asDomainDepartment())
        assert(member.role == "Supervisor".asDomainRole())
    }
}