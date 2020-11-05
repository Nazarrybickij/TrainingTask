package com.nazarrybickij.trainingtask.db

import androidx.room.*
import com.nazarrybickij.trainingtask.entities.UserDataEntity


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(usersDataEntity: List<UserDataEntity>)

    @Delete
    fun delete(usersDataEntity: UserDataEntity)

    @Query("DELETE FROM UserDataEntity")
    fun delete()

    @Query("SELECT * FROM UserDataEntity")
    fun getAll(): List<UserDataEntity>

    @Query("SELECT * FROM UserDataEntity WHERE id=:id")
    fun getUserById(id: Int): List<UserDataEntity>
}