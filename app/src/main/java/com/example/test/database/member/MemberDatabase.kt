package com.example.test.database.member

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Member database
 *
 * @constructor Create empty Member database
 */
@Database(entities = [DatabaseMember::class], version = 4, exportSchema = false)
abstract class MemberDatabase : RoomDatabase() {
    abstract val memberDatabaseDao: MemberDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MemberDatabase? = null

        fun getInstance(context: Context): MemberDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MemberDatabase::class.java,
                        "member_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}