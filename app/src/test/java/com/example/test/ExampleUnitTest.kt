package com.example.test

import com.example.test.database.member.asDomainDepartment
import com.example.test.domain.Customer
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun testGetters() {
        val customer = Customer(
            id = 1,
            email = "example@example.com",
            name = "John Doe",
            department = "Dit".asDomainDepartment(),
            education = "Bachelor's Degree",
            externType = "External",
            phoneNr = "123-456-7890",
            backupContactId = 2
        )

        // Test getters
        assert(customer.id == 1)
        assert(customer.email == "example@example.com")
        assert(customer.name == "John Doe")
        assert(customer.department == "Dit".asDomainDepartment())
        assert(customer.education == "Bachelor's Degree")
        assert(customer.externType == "External")
        assert(customer.phoneNr == "123-456-7890")
        assert(customer.backupContactId == 2)
    }

    @Test
    fun testSetters() {
        val customer = Customer(
            id = 1,
            email = "example@example.com",
            name = "John Doe",
            department = "Sales".asDomainDepartment(),
            education = "Bachelor's Degree",
            externType = "External",
            phoneNr = "123-456-7890",
            backupContactId = 2
        )

        // Test setters
        customer.id = 2
        customer.email = "new@example.com"
        customer.name = "Jane Smith"
        customer.department = "Sales".asDomainDepartment()
        customer.education = "Master's Degree"
        customer.externType = null
        customer.phoneNr = "987-654-3210"
        customer.backupContactId = 3

        assert(customer.id == 2)
        assert(customer.email == "new@example.com")
        assert(customer.name == "Jane Smith")
        assert(customer.department == "Sales".asDomainDepartment())
        assert(customer.education == "Master's Degree")
        assert(customer.externType == null)
        assert(customer.phoneNr == "987-654-3210")
        assert(customer.backupContactId == 3)
    }
}
