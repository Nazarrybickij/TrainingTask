package com.nazarrybickij.trainingtask.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazarrybickij.trainingtask.db.DBRepository
import com.nazarrybickij.trainingtask.entities.UserDataEntity
import com.nazarrybickij.trainingtask.entities.UserEntity
import com.nazarrybickij.trainingtask.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel:ViewModel() {
    private val networkRepository = NetworkRepository()
    private val userLiveData = MutableLiveData<Pair<Boolean,UserEntity>>()
    private val dbRepository = DBRepository.getInstance()

    fun getUser(id:Int): MutableLiveData<Pair<Boolean,UserEntity>> {
        networkRepository.loadUser(userLiveData,id)
        return userLiveData
    }

}