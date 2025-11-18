package com.example.a1gworkapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SalaryEntity::class, ScheduleEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun appDao(): AppDao

    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instace = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "1galaxy_database"
                ).build()
                INSTANCE = instace
                instace
            }
        }
    }
}