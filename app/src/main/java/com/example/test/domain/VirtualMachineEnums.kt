package com.example.test.domain

enum class Mode(val printableName: String) {
    IAAS("IAAS"),
    AI("AI template"),
    MS_SQL("Microsoft SQL Server template"),
    MySQL("MySQL template"),
    PostgreSQL("PostgreSQL template"),
    MongoDB("MongoDB template")

}

enum class Availability(val printableName: String) {
    BUSINESS_HOURS("Business hours"),
    ALWAYS("24/7")
}

enum class State(val printableName: String) {
    Requested("Requested"),
    InProgress("In progress"),
    Denied("Denied"),
    Accepted("Accepted"),
}