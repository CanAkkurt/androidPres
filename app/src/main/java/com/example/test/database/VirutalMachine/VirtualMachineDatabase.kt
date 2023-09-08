package com.example.test.database.VirutalMachine

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [DatabaseVirtualMachine::class], version = 12, exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class)
abstract class VirtualMachineDatabase : RoomDatabase() {
    abstract val virtualMachineDatabaseDao: VirtualMachineDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: VirtualMachineDatabase? = null

        fun getInstance(context: Context): VirtualMachineDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VirtualMachineDatabase::class.java,
                        "virtualMachine_database"
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