package com.example.test.database.customer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/*
 *
 * Contains functions to insert and get Actors
 * Note: 2 insert-methods --> one insert only one Actor and the other takes a vararg Actors to insert all Actors
 * Note: getActorsLive --> return a list of Actors as live data
 *
 * IMPORTANT: the DAO only knows about DatabaseActors
 *
 */
@Dao
interface CustomerDatabaseDao {

    @Insert
    fun insert(customer: DatabaseCustomer)

    // Adding an insert all with a vararg parameter. Replace strategy is upsert (updating if exists, inserting when not existing)
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//     fun insertAll(vararg customers: DatabaseCustomer)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg customers: DatabaseCustomer)

    @Query("SELECT * FROM customer_table ORDER BY id ASC")
    fun getAllCustomers(): List<DatabaseCustomer>

    @Query("SELECT * FROM customer_table ORDER BY id ASC")
    fun getAllCustomersLive(): LiveData<List<DatabaseCustomer>>

    @Query("SELECT * FROM customer_table WHERE id = :id")
    fun getCustomerById(id: Int): DatabaseCustomer?

}