package com.example.test.database.VirutalMachine

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface VirtualMachineDatabaseDao {

    @Insert
    suspend fun insert(customer: DatabaseVirtualMachine)

    // Adding an insert all with a vararg parameter. Replace strategy is upsert (updating if exists, inserting when not existing, https://betterprogramming.pub/upserting-in-room-8207a100db53)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg actors: DatabaseVirtualMachine)

    @Query("SELECT * FROM virtual_machine_table ORDER BY id ASC")
    suspend fun getAllVirtualMachines(): List<DatabaseVirtualMachine>

    @Query("SELECT * FROM virtual_machine_table ORDER BY id ASC")
    fun getAllVirtualMachinesLive(): LiveData<List<DatabaseVirtualMachine>>

    @Query("SELECT * FROM virtual_machine_table WHERE id = :id")
    suspend fun getVirtualMachineById(id: Int): DatabaseVirtualMachine?

}