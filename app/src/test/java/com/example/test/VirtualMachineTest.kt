package com.example.test

import com.example.test.domain.State
import com.example.test.domain.VirtualMachine
import org.junit.Test
import java.time.LocalDateTime

class VirtualMachineTest {


    @Test
    fun testGetters() {
        val virtualMachine = VirtualMachine(
            name = "VM-001",
            id = 1,
            state = State.Accepted,
            vcpUAmount = 4,
            memoryAmount = 8192,
            storageAmount = 1024,
            active = "Yes",
            vcpUAmountInUse = 2,
            memoryAmountInUse = 4096,
            storageAmountInUse = 512,
            startDate = LocalDateTime.of(2023, 9, 7, 0, 0),
            endDate = LocalDateTime.of(2023, 9, 14, 0, 0)
        )

        // Test getters
        assert(virtualMachine.name == "VM-001")
        assert(virtualMachine.id == 1)
        assert(virtualMachine.state == State.Accepted)
        assert(virtualMachine.vcpUAmount == 4)
        assert(virtualMachine.memoryAmount == 8192)
        assert(virtualMachine.storageAmount == 1024)
        assert(virtualMachine.active == "Yes")
        assert(virtualMachine.vcpUAmountInUse == 2)
        assert(virtualMachine.memoryAmountInUse == 4096)
        assert(virtualMachine.storageAmountInUse == 512)
        assert(virtualMachine.startDate == LocalDateTime.of(2023, 9, 7, 0, 0))
        assert(virtualMachine.endDate == LocalDateTime.of(2023, 9, 14, 0, 0))
    }

    @Test
    fun testSetters() {
        val virtualMachine = VirtualMachine(
            name = "VM-001",
            id = 1,
            state = State.Accepted,
            vcpUAmount = 4,
            memoryAmount = 8192,
            storageAmount = 1024,
            active = "Yes",
            vcpUAmountInUse = 2,
            memoryAmountInUse = 4096,
            storageAmountInUse = 512,
            startDate = LocalDateTime.of(2023, 9, 7, 0, 0),
            endDate = LocalDateTime.of(2023, 9, 14, 0, 0)
        )

        // Test setters
        virtualMachine.name = "VM-002"
        virtualMachine.id = 2
        virtualMachine.state = State.Denied
        virtualMachine.vcpUAmount = 2
        virtualMachine.memoryAmount = 4096
        virtualMachine.storageAmount = 512
        virtualMachine.active = "No"
        virtualMachine.vcpUAmountInUse = 1
        virtualMachine.memoryAmountInUse = 2048
        virtualMachine.storageAmountInUse = 256
        virtualMachine.startDate = LocalDateTime.of(2023, 9, 14, 0, 0)
        virtualMachine.endDate = LocalDateTime.of(2023, 9, 21, 0, 0)

        assert(virtualMachine.name == "VM-002")
        assert(virtualMachine.id == 2)
        assert(virtualMachine.state == State.Denied)
        assert(virtualMachine.vcpUAmount == 2)
        assert(virtualMachine.memoryAmount == 4096)
        assert(virtualMachine.storageAmount == 512)
        assert(virtualMachine.active == "No")
        assert(virtualMachine.vcpUAmountInUse == 1)
        assert(virtualMachine.memoryAmountInUse == 2048)
        assert(virtualMachine.storageAmountInUse == 256)
        assert(virtualMachine.startDate == LocalDateTime.of(2023, 9, 14, 0, 0))
        assert(virtualMachine.endDate == LocalDateTime.of(2023, 9, 21, 0, 0))
    }

}