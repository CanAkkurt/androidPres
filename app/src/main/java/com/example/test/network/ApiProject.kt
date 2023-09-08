package com.example.test.network


import com.example.test.database.project.DatabaseProject
import com.example.test.domain.Project
import com.squareup.moshi.Json

data class ApiProject(

    @Json(name = "id")
    var projectId: Int,

    @Json(name = "name")
    val projectName: String,

    @Json(name = "customerName")
    val customerName: String
)

fun ApiProject.asDomainProject(): Project {
    return Project(
        id = projectId,
        name = projectName,
        customerName = customerName
    )
}

fun ApiProject.asDatabaseProject(): DatabaseProject {
    return DatabaseProject(
        id = projectId,
        name = projectName,
        customerName = customerName

    )
}


// Container that helps us parsing the api response into multiple domain Projects
data class ApiProjectContainer(
    val apiProjects: List<ApiProject>
)


data class ApiProjectContainers(
    val projects: List<ApiProject>
)

fun List<ApiProject>.asDatabaseProject(): Array<DatabaseProject> {
    return map { it.asDatabaseProject() }.toTypedArray()
}

// Convert network result into domain Projects
fun ApiProjectContainer.asDomainModels(): List<Project> {
    return apiProjects.map { it.asDomainProject() }
}

fun List<ApiProject>.asDomainModels(): List<Project> {
    return this.map { it.asDomainProject() }
}

fun ApiProjectContainer.asDatabaseProject(): List<DatabaseProject> {
    return apiProjects.map { it.asDatabaseProject() }
}

