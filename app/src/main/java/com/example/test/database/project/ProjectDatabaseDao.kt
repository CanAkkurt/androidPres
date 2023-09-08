package com.example.test.database.project

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
interface ProjectDatabaseDao {

    @Insert
    suspend fun insert(project: DatabaseProject)

    // Adding an insert all with a vararg parameter. Replace strategy is upsert (updating if exists, inserting when not existing, https://betterprogramming.pub/upserting-in-room-8207a100db53)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg actors: DatabaseProject)

    @Query("SELECT * FROM project_table ORDER BY id ASC")
    suspend fun getAllProjects(): List<DatabaseProject>

    @Query("SELECT * FROM project_table ORDER BY id ASC")
    fun getAllProjectsLive(): LiveData<List<DatabaseProject>>

    @Query("SELECT * FROM project_table WHERE id = :id")
    suspend fun getProjectById(id: Int): DatabaseProject?

}