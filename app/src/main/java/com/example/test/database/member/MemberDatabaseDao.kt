package com.example.test.database.member

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/*
 *
 * Contains functions to insert and get Members
 * Note: 2 insert-methods --> one insert only one Members and the other takes a vararg Actors to insert all Members
 * Note: getMembersLive --> return a list of Members as live data
 *
 * IMPORTANT: the DAO only knows about DatabaseMembers
 *
 */
@Dao
interface MemberDatabaseDao {

    @Insert
    suspend fun insert(member: DatabaseMember)

    // Adding an insert all with a vararg parameter. Replace strategy is upsert (updating if exists, inserting when not existing, https://betterprogramming.pub/upserting-in-room-8207a100db53)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg members: DatabaseMember)

    @Query("SELECT * FROM member_table ORDER BY id ASC")
    suspend fun getAllMembers(): List<DatabaseMember>

    @Query("SELECT * FROM member_table ORDER BY id ASC")
    fun getAllMembersLive(): LiveData<List<DatabaseMember>>

    @Query("SELECT * FROM member_table WHERE id = :id")
    suspend fun getMemberById(id: Int): DatabaseMember?

}