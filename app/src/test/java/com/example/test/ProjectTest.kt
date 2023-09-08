package com.example.test

import com.example.test.domain.Project
import org.junit.Test

class ProjectTest {


    @Test
    fun testGetters() {
        val project = Project(id = 1, name = "Project A", customerName = "Customer X")

        // Test getters
        assert(project.id == 1)
        assert(project.name == "Project A")
        assert(project.customerName == "Customer X")
    }

    @Test
    fun testSetters() {
        val project = Project(id = 1, name = "Project A", customerName = "Customer X")

        // Test setters
        project.id = 2
        project.name = "Project B"
        project.customerName = "Customer Y"

        assert(project.id == 2)
        assert(project.name == "Project B")
        assert(project.customerName == "Customer Y")
    }


}