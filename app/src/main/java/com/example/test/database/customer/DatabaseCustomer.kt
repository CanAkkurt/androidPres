package com.example.test.database.customer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test.database.member.asDomainDepartment
import com.example.test.domain.Customer
import com.example.test.domain.Department
import com.example.test.domain.Role

@Entity(tableName = "customer_table")
data class DatabaseCustomer(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "customer_name")
    var name: String,

    @ColumnInfo(name = "customer_mail")
    var email: String,

    @ColumnInfo(name = "customer_education")
    var education: String,

    @ColumnInfo(name = "customer_extern_type")
    var externType: String?,

    @ColumnInfo(name = "customer_phone_nr")
    var phoneNr: String,


    @ColumnInfo(name = "customer_department")
    var department: String,


    @ColumnInfo(name = "customer_backup_contact_id")
    var backupContactId: Int
)

// Convert a single DatabaseCustomer into a normal domain Customer
fun DatabaseCustomer.asDomainModel(): Customer {
    return Customer(
        id = id,
        name = name,
        email = email,
        education = education,
        externType = externType,
        phoneNr = phoneNr,
        department = "dit".asDomainDepartment(), //department.asDomainDepartment()
        backupContactId = backupContactId // Relatie naar zichzelf?
    )
}

// Convert a list of DatabaseCustomers in a list of normal domain Customers
fun List<DatabaseCustomer>.asDomainModel(): List<Customer> {
    return map { it.asDomainModel() }
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