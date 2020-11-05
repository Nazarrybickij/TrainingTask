package com.nazarrybickij.trainingtask.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazarrybickij.trainingtask.db.DBRepository
import com.nazarrybickij.trainingtask.entities.UserDataEntity
import com.nazarrybickij.trainingtask.entities.UsersPageEntity
import com.nazarrybickij.trainingtask.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersPageViewModel:ViewModel() {
    private val networkRepository = NetworkRepository()
    private val usersPageLiveData = MutableLiveData<Pair<Boolean,UsersPageEntity>>()
    private val dbRepository = DBRepository.getInstance()
    private var lastPage = 0
    var page = 1
    var totalPage = 2

    fun getPageUsers(): MutableLiveData<Pair<Boolean,UsersPageEntity>> {
        if (totalPage >= page && lastPage != page){
            networkRepository.loadPage(usersPageLiveData,page)
            lastPage = page
        }
        return usersPageLiveData
    }

    fun saveUsers(list: List<UserDataEntity>){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insert(list)
        }
    }
}