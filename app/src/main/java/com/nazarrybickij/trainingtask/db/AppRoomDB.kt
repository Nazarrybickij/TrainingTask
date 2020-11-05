package com.nazarrybickij.trainingtask.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nazarrybickij.trainingtask.entities.UserDataEntity

@Database(entities = [UserDataEntity::class], version = 1, exportSchema = false)
 abstract class AppRoomDB : RoomDatabase() {
    abstract fun userDao(): UserDao

}