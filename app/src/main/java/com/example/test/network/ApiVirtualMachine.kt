package com.example.test.network

import com.example.test.database.VirutalMachine.DatabaseVirtualMachine
import com.example.test.domain.Availability
import com.example.test.domain.Mode
import com.example.test.domain.State
import com.example.test.domain.VirtualMachine
import com.squareup.moshi.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ApiVirtualMachine(

    @Json(name = "name")
    var name: String,
    @Json(name = "id")
    var id: Int,
//    @Json(name = "hostname")
//    var hostname: String,
//    @Json(name = "fqdn")
//    var fqdn: String,
//    @Json(name = "mode")
//    var mode: String,
//    @Json(name = "backup_frequency")
//    var backupFrequency: Int,
//    @Json(name = "availability")
//    var availability: String,
//    @Json(name = "host_server")
//    var hostServer: Int,
//    @Json(name = "cluster")
//    var cluster: Int,
//    @Json(name = "ports")
//    var ports: IntArray,
    @Json(name = "state")
    var state: String,
    @Json(name = "vcpUamount")
    var vcpUAmount: Int,
    @Json(name = "memoryAmount")
    var memoryAmount: Int,
    @Json(name = "storageAmount")
    var storageAmount: Int,


    @Json(name = "vcpuInUse")
    var vcpUAmountInUse: Int,
    @Json(name = "memoryInUse")
    var memoryAmountInUse: Int,
    @Json(name = "storageInUse")
    var storageAmountInUse: Int,

    @Json(name = "active")
    var active: String,


//    @Json(name = "high_availability")
//    var highAvailability: Boolean,
//    @Json(name = "request_date")
//    var requestDate: String,
    @Json(name = "startDate")
    var startDate: String,
    @Json(name = "endDate")
    var endDate: String,

    @Json(name = "fysiekeServer")
    var server: String


)

fun ApiVirtualMachine.asDomainVirtualMachine(): VirtualMachine {
    return VirtualMachine(
        id = id,
        name = name,
//        hostname = hostname,
//        fqdn = fqdn,
//        mode = mode.asDomainMode(),
//        backupFrequency = backupFrequency,
//        availability = availability.asDomainAvailability(),
//        hostServer = hostServer,
//        cluster = cluster,
        state = state.asDomainState(),
        vcpUAmount = vcpUAmount,
        memoryAmount = memoryAmount,
        storageAmount = storageAmount,
        vcpUAmountInUse = vcpUAmountInUse,
        memoryAmountInUse = memoryAmountInUse,
        storageAmountInUse = storageAmountInUse,
        active = active,

//        highAvailability = highAvailability,
//        requestDate = requestDate.asDomainDate(requestDate),
        startDate = asDomainDate(startDate),
        endDate = asDomainDate(endDate),
        fySiekeServer = server


    )
}

fun ApiVirtualMachine.asDatabaseVirtualMachine(): DatabaseVirtualMachine {
    return DatabaseVirtualMachine(
        id = id,
        name = name,
//        hostname = hostname,
//        fqdn = fqdn,
//        mode = mode,
//        backupFrequency = backupFrequency,
//        availability = availability,
//        hostServer = hostServer,
//        cluster = cluster,
        state = state,
        vcpUAmount = vcpUAmount,
        memoryAmount = memoryAmount,
        storageAmount = storageAmount,


        vcpUAmountInUse = vcpUAmountInUse,
        memoryAmountInUse = memoryAmountInUse,
        storageAmountInUse = storageAmountInUse,
        active = active,
//        highAvailability = highAvailability,
//        requestDate = requestDate,
        startDate = asDomainDate(startDate),
        endDate = asDomainDate(endDate),
        fysiekeServer = server
    )
}

// Container that helps us parsing the api response into multiple domain VirtualMachines
data class ApiVirtualMachineContainer(
    val virtualMachines: List<ApiVirtualMachine>
)


// Convert network result into domain VirtualMachines
fun ApiVirtualMachineContainer.asDomainModels(): List<VirtualMachine> {
    return virtualMachines.map { it.asDomainVirtualMachine() }
}

fun List<ApiVirtualMachine>.asDomainModels(): List<VirtualMachine> {
    return this.map { it.asDomainVirtualMachine() }
}

// Convert a list of ApiVirtualMachines into a list of DatabaseVirtualMachines (this can then be used in the insert call as vararg)
fun List<ApiVirtualMachine>.asDatabaseVirtualMachine(): Array<DatabaseVirtualMachine> {
    return map { it.asDatabaseVirtualMachine() }.toTypedArray()
}


// Convert network result into DatabaseVirtualMachine
fun ApiVirtualMachineContainer.asDatabaseVirtualMachine(): List<DatabaseVirtualMachine> {
    return virtualMachines.map { it.asDatabaseVirtualMachine() }
}


fun String.asDomainMode(): Mode {
    if (lowercase().equals("iaas")) {
        return Mode.IAAS
    } else if (lowercase().equals("ai")) {
        return Mode.AI
    } else if (lowercase().equals("ms_sql")) {
        return Mode.MS_SQL
    } else if (lowercase().equals("mysql")) {
        return Mode.MySQL
    } else if (lowercase().equals("postgresql")) {
        return Mode.PostgreSQL
    }
    return Mode.MongoDB

}

fun String.asDomainAvailability(): Availability {
    if (lowercase().equals("business_hours")) {
        return Availability.BUSINESS_HOURS
    }
    return Availability.ALWAYS

}

fun String.asDomainState(): State {
    if (lowercase().equals("requested")) {
        return State.Requested
    } else if (lowercase().equals("inprogress")) {
        return State.InProgress
    } else if (lowercase().equals("denied")) {
        return State.Denied
    }
    return State.Accepted

}

fun asDomainDate(date: String): LocalDateTime {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val result: LocalDateTime = LocalDateTime.parse(date, format)



    return result

}