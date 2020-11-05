package com.nazarrybickij.trainingtask.db

import android.util.Log
import com.nazarrybickij.trainingtask.App
import com.nazarrybickij.trainingtask.entities.UserDataEntity

class DBRepository(var appRoomDB: AppRoomDB) {
    fun insert(usersDataEntity:List<UserDataEntity>) {
        appRoomDB.userDao().delete()
        appRoomDB.userDao().insertAll(usersDataEntity)
    }

    fun getAll(): List<UserDataEntity> {
       return  appRoomDB.userDao().getAll()
    }
    fun getUserByID(id:Int): List<UserDataEntity> {
        return appRoomDB.userDao().getUserById(id)
    }

    companion object {
        private  var instance: DBRepository? = null
        fun getInstance(): DBRepository {
                if (instance == null) {
                    instance = DBRepository(App.database)
                }
                return instance as DBRepository
            }
    }
}