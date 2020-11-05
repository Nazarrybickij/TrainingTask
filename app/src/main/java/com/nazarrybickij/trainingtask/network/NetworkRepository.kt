package com.nazarrybickij.trainingtask.network

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.nazarrybickij.trainingtask.App
import com.nazarrybickij.trainingtask.entities.AdEntity
import com.nazarrybickij.trainingtask.entities.UserDataEntity
import com.nazarrybickij.trainingtask.entities.UserEntity
import com.nazarrybickij.trainingtask.entities.UsersPageEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository() {
    fun loadUser(postLiveData: MutableLiveData<Pair<Boolean, UserEntity>>, id: Int) {
        NetworkService.instance?.jSONApi?.getUserById(id)?.enqueue(object :
            Callback<UserEntity?> {
            override fun onResponse(
                call: Call<UserEntity?>,
                response: Response<UserEntity?>
            ) {
                val post: UserEntity
                if (response.isSuccessful && response.body() != null) {
                    post = response.body()!!
                    var pair = Pair(response.isSuccessful, post)
                    postLiveData.value = pair
                }
            }

            override fun onFailure(call: Call<UserEntity?>, t: Throwable) {
                Toast.makeText(App.context, "Error network", Toast.LENGTH_LONG).show()
                val usersPageEntity =
                    UserEntity(AdEntity("", "", ""), UserDataEntity("", "", "", 0, ""))
                val pair = Pair(false, usersPageEntity)
                postLiveData.value = pair
            }
        })
    }

    fun loadPage(postLiveData: MutableLiveData<Pair<Boolean, UsersPageEntity>>, page: Int) {
        NetworkService.instance?.jSONApi?.getPageUsers(page)?.enqueue(object :
            Callback<UsersPageEntity?> {
            override fun onResponse(
                call: Call<UsersPageEntity?>,
                response: Response<UsersPageEntity?>
            ) {
                val post: UsersPageEntity
                if (response.isSuccessful && response.body() != null) {
                    post = response.body()!!
                    if (postLiveData.value == null) {
                        var pair = Pair(response.isSuccessful, post)
                        postLiveData.value = pair
                        return
                    }
                    val v = postLiveData.value
                    val l = v?.second?.data as MutableList
                    l.addAll(post.data)
                    v.second.data = l
                    val pair = Pair(response.isSuccessful, v.second)
                    postLiveData.value = pair
                } else {
                    Toast.makeText(App.context, "Error network", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UsersPageEntity?>, t: Throwable) {
                Toast.makeText(App.context, "Error network", Toast.LENGTH_LONG).show()
                val usersPageEntity =
                    UsersPageEntity(AdEntity("", "", ""), mutableListOf(), 0, 0, 0, 2)
                val pair = Pair(false, usersPageEntity)
                postLiveData.value = pair
            }
        })
    }

}